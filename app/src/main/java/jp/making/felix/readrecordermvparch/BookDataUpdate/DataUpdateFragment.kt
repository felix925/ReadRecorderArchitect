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

class DataUpdateFragment : Fragment(), DataUpdateContract.View {
    @Inject
    lateinit var presenter: DataUpdateContract.Presenter
    val args: DataViewFragmentArgs by navArgs()
    lateinit var binding: BookUpdateFragmentBinding

    override fun onAttach(context: Context) {
        (activity!!.application as App).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter.attachView(this)
        binding = BookUpdateFragmentBinding.inflate(inflater, container, false)
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
        return binding.root
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
        Log.d("maxPage",maxPage.toString())
        binding.pagePleaseText.text = binding.pagePleaseText.text.toString() + "\n最大ページ(${maxPage})"
    }
}