package jp.making.felix.readrecordermvparch.BookListView

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import jp.making.felix.readrecorder.ListAdapter
import jp.making.felix.readrecordermvparch.Base.BaseFragment
import jp.making.felix.readrecordermvparch.DI.App
import jp.making.felix.readrecordermvparch.R
import jp.making.felix.readrecordermvparch.data.BookModel.Book
import jp.making.felix.readrecordermvparch.databinding.BookListFragmentBinding
import javax.inject.Inject

class ListViewFragment : Fragment(),ListViewContract.View,BaseFragment{

    @Inject lateinit var presenter:ListViewContract.Presenter
//    private lateinit var binding: BookListFragmentBinding
    override fun onAttach(context: Context) {
        (activity!!.application as App).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View?{
//        val view = inflater.inflate(R.layout.book_list_fragment,container,false)
//        binding = BookListFragmentBinding.inflate(layoutInflater)
        presenter.attachView(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.commonList.fab.setOnClickListener{
//            FabAction()
//        }
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
    /**
     * リスト上のアイテムがタップされると呼びだされる
     * ここからidを取得してintentに保存してFragment移動をViewでさせる
     * */
    override fun pressBooks(count:Int) {
//        binding.BookList.isVisible = false
        val action = ListViewFragmentDirections.actionListToData(count.toString())
        findNavController().navigate(action)
    }
    /**
     * リストアダプタと繋げてDaoから取得したデータを渡す
     *
     * */
    override fun showAllBooks(books: List<Book>) {
        context?.apply {
//            binding.BookList.adapter = ListAdapter(this,books)
//            binding.BookList.setOnItemClickListener{_, _, _, id ->
//                pressBooks(id.toInt())
//            }
        }
    }
    /**
     * FABを押された際の画面遷移を行う
     * */
    
    override fun FabAction() {
//        binding.BookList.isVisible = false
        findNavController().navigate(R.id.action_list_to_regist)
    }
}