package jp.making.felix.readrecordermvparch.BookDataView

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.mikephil.charting.charts.LineChart
import com.google.android.material.floatingactionbutton.FloatingActionButton
import jp.making.felix.readrecordermvparch.Base.BaseFragment
import jp.making.felix.readrecordermvparch.R
import jp.making.felix.readrecordermvparch.data.Model.BookRepository
import jp.making.felix.readrecordermvparch.data.Model.LocalBookModel
import jp.making.felix.readrecordermvparch.data.Model.RemoteBookModel
import jp.making.felix.readrecordermvparch.data.Page
import kotlinx.android.synthetic.main.book_data_fragment.*
import java.util.*

class DataViewFragment: Fragment(), DataViewContract.View, BaseFragment {
    override lateinit var presenter: DataViewContract.Presenter
    val args:DataViewFragmentArgs by navArgs()
    lateinit var chart:LineChart
    lateinit var list:ListView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DataViewPresenter(LocalBookModel(),this)
        val view = inflater.inflate(R.layout.book_data_fragment,container,false)
        DataViewPresenter(BookRepository(LocalBookModel(),RemoteBookModel()),this)
        chart = view.findViewById(R.id.pagechart)
        list = view.findViewById(R.id.thoughtList)
        Log.i("chartData",chart.toString())
        // Bundleを取得する
        presenter.getBookId(args.BOOKID).let {
            SetUpChart(it,chart)
            SetUpThoughtList(it,list)
        }
        activity?.findViewById<FloatingActionButton>(R.id.fab)?.setOnClickListener{
            FabAction()
        }
        return view
    }

    override fun FabAction() {
        findNavController().navigate(R.id.action_data_to_list)
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
                pressThought(id.toInt())
            }
        }
    }
    private fun pressThought(id:Int){
        
    }
}