package jp.making.felix.readrecordermvparch.BookListView

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.internal.DaggerCollections
import jp.making.felix.readrecorder.ListAdapter
import jp.making.felix.readrecordermvparch.Base.BaseFragment
import jp.making.felix.readrecordermvparch.R
import jp.making.felix.readrecordermvparch.data.Book
import jp.making.felix.readrecordermvparch.data.Model.BookRepository
import jp.making.felix.readrecordermvparch.data.Model.LocalBookModel
import jp.making.felix.readrecordermvparch.data.Model.RemoteBookModel
import kotlinx.android.synthetic.main.book_list_fragment.*

class ListViewFragment : Fragment(),ListViewContract.View,BaseFragment{

    override lateinit var presenter:ListViewContract.Presenter
    lateinit var fab:FloatingActionButton
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View?{
        val view = inflater.inflate(R.layout.book_list_fragment,container,false)

        ListViewPresenter(LocalBookModel(),this)
        activity?.findViewById<FloatingActionButton>(R.id.fab)?.let {
            fab = it
        }
        fab.setOnClickListener {
            FabAction()
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
        Log.i("BOOK_ID",count.toString())
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
                Log.i("image",books[id.toInt()].imageUrl)
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
        findNavController().navigate(R.id.action_list_to_regist)
    }
}