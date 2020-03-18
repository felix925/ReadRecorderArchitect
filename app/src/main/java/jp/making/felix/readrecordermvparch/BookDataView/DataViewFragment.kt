package jp.making.felix.readrecordermvparch.BookDataView

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.mikephil.charting.charts.LineChart
import com.google.android.material.floatingactionbutton.FloatingActionButton
import jp.making.felix.readrecordermvparch.Base.BaseFragment
import jp.making.felix.readrecordermvparch.BookListView.MainActivity
import jp.making.felix.readrecordermvparch.DI.App
import jp.making.felix.readrecordermvparch.R
import jp.making.felix.readrecordermvparch.data.BookModel.Page
import javax.inject.Inject

class DataViewFragment: Fragment(), DataViewContract.View, BaseFragment {
    @Inject
    lateinit var presenter: DataViewContract.Presenter
    val args:DataViewFragmentArgs by navArgs()
    lateinit var chart:LineChart
    lateinit var list:ListView

    override fun onAttach(context: Context) {
        (activity!!.application as App).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.book_data_fragment,container,false)
        chart = view.findViewById(R.id.pagechart)
        list = view.findViewById(R.id.thoughtList)
        presenter.attachView(this)
        // Bundleを取得する
        presenter.getBookId(args.BOOKID).let {
            SetUpChart(it,chart)
            SetUpThoughtList(it,list)
        }
        activity?.let {
            (activity as MainActivity).mainBinding.fab.setOnClickListener{
                FabAction()
            }
        }
        return view
    }

    override fun FabAction() {
        val action = DataViewFragmentDirections.actionDataToUpdate(presenter.getBookId(args.BOOKID))
        findNavController().navigate(action)
    }

    /**
     * 待機するときのぐるぐる回るやつ（プログレスバー）を表示する
     * */
    override fun showProgress() {
        activity?.findViewById<ProgressBar>(R.id.mainProgress)?.let{
            it.visibility = ProgressBar.VISIBLE
        }
    }
    /**
     *　待機するときのぐるぐる回るやつ（プログレスバー）を非表示にする
     * */
    override fun deleteProgress() {
        activity?.findViewById<ProgressBar>(R.id.mainProgress)?.let{
            it.visibility = ProgressBar.INVISIBLE
        }
    }

    override fun setUpButtonIcon() {
    }

    private fun SetUpChart(position:String,lineChart: LineChart){
        Log.i("called","setUpChart is called")
        val chartAdapt = ChartAdapter()
        val pageData:Pair<Array<Page>,Int> = presenter.getPageData(position)
        lineChart.data = chartAdapt.setUpChart(lineChart,pageData.first,pageData.second)
    }

    private fun SetUpThoughtList(position: String,list:ListView){
        val thought = presenter.getThoughtData(position).toList()
        val page = presenter.getPageData(position).first.toList()
        context?.apply {
            list.adapter = thoughtListAdapter(this,thought,page)
            list.setOnItemClickListener{_, _, _, id ->
                pressThought(thought[id.toInt()].logData,page[id.toInt()].pageData)
            }
        }
    }

    private fun pressThought(thought:String,id:Int){
        val dialog = AlertDialog.Builder(this.context!!)
        dialog.setTitle(id.toString() + "ページ目の感想")
        dialog.setMessage(thought)
        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView()
    }
}