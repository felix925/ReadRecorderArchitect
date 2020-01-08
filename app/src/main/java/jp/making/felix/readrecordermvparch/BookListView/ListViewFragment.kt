package jp.making.felix.readrecordermvparch.BookListView

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import jp.making.felix.readrecorder.ListAdapter
import jp.making.felix.readrecordermvparch.Base.BaseFragment
import jp.making.felix.readrecordermvparch.R
import jp.making.felix.readrecordermvparch.data.Book
import jp.making.felix.readrecordermvparch.data.Dao.BookDao
import kotlinx.android.synthetic.main.book_list_fragment.*

class ListViewFragment : Fragment(),ListViewContract.View,BaseFragment{

    override lateinit var presenter:ListViewContract.Presenter
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View?{
        val view = inflater.inflate(R.layout.book_list_fragment,container,false)
        ListViewPresenter(BookDao(),this)
        activity?.findViewById<FloatingActionButton>(R.id.fab)?.setOnClickListener {
                FabAction()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.start()
    }

    override fun showProgress() {
        view?.findViewById<ProgressBar>(R.id.mainProgress)?.let{
            it.visibility = ProgressBar.VISIBLE
        }
    }

    override fun deleteProgress() {
        view?.findViewById<ProgressBar>(R.id.mainProgress)?.let{
            it.visibility = ProgressBar.INVISIBLE
        }
    }
    /**
     * リスト上のアイテムがタップされると呼びだされる
     * ここからidを取得してintentに保存してFragment移動をViewでさせる
     * */
    override fun showBooks(count:Int) {
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
                showBooks(id.toInt())
            }
        }
    }

    fun setUpButtonIcon(){
        view?.findViewById<Button>(R.id.fab)?.let{

        }
    }

    override fun FabAction() {
        Log.d("Fab","Press")
    }
}