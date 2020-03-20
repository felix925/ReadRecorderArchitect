package jp.making.felix.readrecordermvparch.BookListView

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import jp.making.felix.readrecorder.ListAdapter
import jp.making.felix.readrecordermvparch.Base.BaseFragment
import jp.making.felix.readrecordermvparch.DI.App
import jp.making.felix.readrecordermvparch.R
import jp.making.felix.readrecordermvparch.data.BookModel.Book
//import jp.making.felix.readrecordermvparch.databinding.BookListFragmentBinding
import kotlinx.android.synthetic.main.book_list_fragment.*
import javax.inject.Inject

class ListViewFragment : Fragment(),ListViewContract.View,BaseFragment{

    @Inject lateinit var presenter:ListViewContract.Presenter
    lateinit var fab:FloatingActionButton

    override fun onAttach(context: Context) {
        (activity!!.application as App).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View?{
        val view = inflater.inflate(R.layout.book_list_fragment,container,false)
        presenter.attachView(this)
        activity?.let {
            fab = it.findViewById<FloatingActionButton>(R.id.fab)
            fab.setOnClickListener {
                FabAction()
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpButtonIcon()
        presenter.start()
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
        context?.apply{
            BookList.isVisible = true
        }
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
        context?.apply {
            BookList.isVisible = false
        }
        val action = ListViewFragmentDirections.actionListToData(count.toString())
        findNavController().navigate(action)
    }
    /**
     * リストアダプタと繋げてDaoから取得したデータを渡す
     *
     * */
    override fun showAllBooks(books: List<Book>) {
        context?.apply {
            BookList.adapter = ListAdapter(this,books)
            BookList.setOnItemClickListener{_, _, _, id ->
                pressBooks(id.toInt())
            }
        }
    }
    /**
     * FAB（フロートアクションボタン）のアイコンの画像を設定する
     * */
    override fun setUpButtonIcon(){
        fab.setImageDrawable(resources.getDrawable(R.drawable.note_add))
    }
    /**
     * FABを押された際の画面遷移を行う
     * */
    
    override fun FabAction() {
        context?.apply {
            BookList.isVisible = false
        }
        findNavController().navigate(R.id.action_list_to_regist)
    }
}