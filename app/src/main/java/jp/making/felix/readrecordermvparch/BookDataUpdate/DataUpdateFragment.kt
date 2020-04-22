package jp.making.felix.readrecordermvparch.BookDataUpdate

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import jp.making.felix.readrecordermvparch.BookDataView.DataViewFragmentArgs
import jp.making.felix.readrecordermvparch.DI.App
import jp.making.felix.readrecordermvparch.R
import jp.making.felix.readrecordermvparch.databinding.BookUpdateFragmentBinding
import javax.inject.Inject

class DataUpdateFragment : Fragment(R.layout.book_update_fragment), DataUpdateContract.View {
    @Inject
    lateinit var presenter: DataUpdateContract.Presenter
    val args: DataViewFragmentArgs by navArgs()
    lateinit var binding: BookUpdateFragmentBinding

    override fun onAttach(context: Context) {
        (activity!!.application as App).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        binding = BookUpdateFragmentBinding.bind(view)
        presenter.getMaxPage(args.BOOKID)
        binding.apply {
            button.setOnClickListener {
                val pageText = pageInput.text
                val thoughtText = thoughtInput.text
                if (pageText.isNotEmpty() && thoughtText.isNotEmpty()) {
                    presenter.updateData(
                        id = args.BOOKID,
                        page = pageText.toString(),
                        thought = thoughtText.toString()
                    )
                    findNavController().navigate(R.id.action_update_to_list)
                } else {
                    showToast("正しく情報を入力してね！")
                }
            }
        }
    }

    override fun deleteProgress() {

    }

    override fun showProgress() {
    }

    override fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView()
    }

    override fun showMaxPage(maxPage: Int) {
        binding.pagePleaseText.text = """${binding.pagePleaseText.text}最大ページ(${maxPage})"""
    }
}