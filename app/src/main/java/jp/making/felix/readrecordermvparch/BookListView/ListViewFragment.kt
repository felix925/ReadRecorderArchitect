package jp.making.felix.readrecordermvparch.BookListView

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import jp.making.felix.readrecordermvparch.DI.App
import jp.making.felix.readrecordermvparch.R
import jp.making.felix.readrecordermvparch.data.BookItem
import jp.making.felix.readrecordermvparch.data.BookModel.Book
import jp.making.felix.readrecordermvparch.databinding.BookListFragmentBinding
import javax.inject.Inject

class ListViewFragment : Fragment(R.layout.book_list_fragment), ListViewContract.View {

    @Inject
    lateinit var presenter: ListViewContract.Presenter
    private lateinit var binding: BookListFragmentBinding
    private var _articleListAdapter: GroupAdapter<GroupieViewHolder>? = null
    //いちいちnullチェックするのを避けるために、notnullを作っておいて、nullを入れたいものは直接使わないようにする。
    private val articleListAdapter get() = requireNotNull(_articleListAdapter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _articleListAdapter = GroupAdapter()
    }

    override fun onAttach(context: Context) {
        (activity!!.application as App).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = BookListFragmentBinding.bind(view)
        presenter.attachView(this)
        binding.BookList.setup()
        presenter.start()
    }

    override fun onResume() {
        super.onResume()
        presenter.attachView(this)
        binding.BookList.setup()
        presenter.start()
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
    override fun pressBooks(result: String) {
        val action = ListViewFragmentDirections.actionListToData(result)
        findNavController().navigate(action)
    }

    override fun showBooks(books: List<Book>) {
        val clickListener: (View) -> Unit = {
            val result = it as TextView
            pressBooks(result.text.toString())
        }
        val list = mutableListOf<BookItem>()
        for (i in books) {
            list.add(list.size, BookItem(i,clickListener))
        }
        articleListAdapter.update(list)
    }

    //binding.BookList.adapter = articleListAdapterと同義
    private fun RecyclerView.setup() {
        adapter = articleListAdapter
    }
}