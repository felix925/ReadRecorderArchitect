package jp.making.felix.readrecordermvparch.BookListView

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import jp.making.felix.readrecorder.ListAdapter
import jp.making.felix.readrecordermvparch.DI.App
import jp.making.felix.readrecordermvparch.data.BookModel.Book
import jp.making.felix.readrecordermvparch.databinding.BookListFragmentBinding
import javax.inject.Inject

class ListViewFragment : Fragment(), ListViewContract.View {

    @Inject
    lateinit var presenter: ListViewContract.Presenter
    private lateinit var binding: BookListFragmentBinding
    override fun onAttach(context: Context) {
        (activity!!.application as App).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BookListFragmentBinding.inflate(inflater, container, false)
        presenter.attachView(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.start()
    }

    override fun onResume() {
        super.onResume()
        presenter.attachView(this)
        presenter.start()
//        binding.BookList.isVisible = true
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView()
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

    /**
     * リスト上のアイテムがタップされると呼びだされる
     * ここからidを取得してintentに保存してFragment移動をViewでさせる
     * */
    override fun pressBooks(count: Int) {
        val action = ListViewFragmentDirections.actionListToData(count.toString())
        findNavController().navigate(action)
    }

    /**
     * リストアダプタと繋げてDaoから取得したデータを渡す
     *
     * */
    override fun showAllBooks(books: List<Book>) {
        context?.apply {
            binding.BookList.adapter = ListAdapter(this, books)
            binding.BookList.setOnItemClickListener { _, _, _, id ->
                pressBooks(id.toInt())
            }
        }
    }
}