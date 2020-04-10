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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import jp.making.felix.readrecordermvparch.Base.BaseFragment
import jp.making.felix.readrecordermvparch.DI.App
import jp.making.felix.readrecordermvparch.R
import jp.making.felix.readrecordermvparch.data.BookModel.Logs
import jp.making.felix.readrecordermvparch.data.BookModel.Page
import javax.inject.Inject

class DataViewFragment: Fragment(), DataViewContract.View, BaseFragment {
    @Inject
    lateinit var presenter: DataViewContract.Presenter
    val args:DataViewFragmentArgs by navArgs()
//    private lateinit var binding:BookDataFragmentBinding

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
//        binding = BookDataFragmentBinding.inflate(layoutInflater)
        presenter.attachView(this)
        // Bundleを取得する
        activity?.let {
            activity?.let {
                it.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener{
                    FabAction()
                }
            }
        }
        return view
    }

    override fun setUpChart(bookId: String, pageData: Pair<Array<Page>, Int>) {
        val chartAdapt = ChartAdapter()
//        binding.pagechart.data = chartAdapt.setUpChart(binding.pagechart, pageData.first, pageData.second)
    }

    override fun setUpThought(bookId: String, logList: List<Logs>, pageList: List<Page>) {
        context?.apply {
//            binding.thoughtList.adapter = thoughtListAdapter(this, logList, pageList)
//            binding.thoughtList.setOnItemClickListener{_, _, _, id ->
//                pressThought(logList[id.toInt()].logData, pageList[id.toInt()].pageData)
//            }
        }
    }

    override fun FabAction() {
        presenter.navigateTrigger(args.BOOKID)
    }

    override fun navigationTrigger(id: String){
        val action = DataViewFragmentDirections.actionDataToUpdate(id)
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