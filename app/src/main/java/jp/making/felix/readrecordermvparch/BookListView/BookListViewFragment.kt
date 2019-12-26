package jp.making.felix.readrecordermvparch.BookListView

import android.os.Bundle
import android.transition.Scene
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import jp.making.felix.readrecorder.BookListAdapter
import jp.making.felix.readrecordermvparch.Base.BaseFragment
import jp.making.felix.readrecordermvparch.R
import jp.making.felix.readrecordermvparch.data.Book
import jp.making.felix.readrecordermvparch.data.Dao.BookDao
import kotlinx.android.synthetic.main.book_list_fragment.*
import kotlin.random.Random

class BookListViewFragment : Fragment(),BookListContract.View,BaseFragment{

    override lateinit var presenter:BookListContract.Presenter
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View?{
        val view = inflater.inflate(R.layout.book_list_fragment,container,false)
        BookListViewPresenter(BookDao(),this)
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
    override fun showBooks(count:Int) {
        Log.i("BOOK_ID",count.toString())
    }

    override fun showAllBooks(books: List<Book>) {
        context?.apply {
            BookList.adapter = BookListAdapter(this,books)
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
        Log.i("FAB_NOW","")
        presenter.registData("name","")
    }
}