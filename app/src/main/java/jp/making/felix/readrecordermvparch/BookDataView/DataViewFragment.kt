package jp.making.felix.readrecordermvparch.BookDataView

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import jp.making.felix.readrecordermvparch.DI.App
import jp.making.felix.readrecordermvparch.R
import jp.making.felix.readrecordermvparch.data.BookModel.Logs
import jp.making.felix.readrecordermvparch.data.BookModel.Page
import jp.making.felix.readrecordermvparch.databinding.BookDataFragmentBinding
import javax.inject.Inject

class DataViewFragment : Fragment(R.layout.book_data_fragment), DataViewContract.View {
    @Inject
    lateinit var presenter: DataViewContract.Presenter
    val args: DataViewFragmentArgs by navArgs()
    private lateinit var binding: BookDataFragmentBinding

    override fun onAttach(context: Context) {
        (activity!!.application as App).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = BookDataFragmentBinding.bind(view)
        presenter.attachView(this)
        presenter.setUpChartAndList(args.BOOKID)
        binding.fab.setOnClickListener {
            FabAction()
        }
    }

    override fun setUpChart(bookId: String, pageData: Pair<Array<Page>, Int>) {
        val chartAdapt = ChartAdapter()
        binding.pagechart.data =
            chartAdapt.setUpChart(binding.pagechart, pageData.first, pageData.second)
    }

    override fun setUpThought(bookId: String, logList: List<Logs>, pageList: List<Page>) {
        context?.apply {
            binding.thoughtList.adapter = thoughtListAdapter(this, logList, pageList)
            binding.thoughtList.setOnItemClickListener { _, _, _, id ->
                pressThought(logList[id.toInt()].logData, pageList[id.toInt()].pageData)
            }
        }
    }

    private fun FabAction() {
        presenter.navigateTrigger(args.BOOKID)
    }

    override fun navigationTrigger(id: String) {
        val action = DataViewFragmentDirections.actionDataToUpdate(id)
        findNavController().navigate(action)
    }

    /**
     * 待機するときのぐるぐる回るやつ（プログレスバー）を表示する
     * */
    override fun showProgress() {
    }

    /**
     *　待機するときのぐるぐる回るやつ（プログレスバー）を非表示にする
     * */
    override fun deleteProgress() {
    }

    private fun pressThought(thought: String, id: Int) {
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