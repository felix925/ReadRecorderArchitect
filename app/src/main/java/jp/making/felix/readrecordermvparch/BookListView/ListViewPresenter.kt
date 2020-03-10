package jp.making.felix.readrecordermvparch.BookListView

import jp.making.felix.readrecordermvparch.data.BookModel.Book
import jp.making.felix.readrecordermvparch.data.Repository.BaseRepository
import jp.making.felix.readrecordermvparch.data.Repository.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ListViewPresenter @Inject constructor(
    private val BookRepository:BookRepository
):ListViewContract.Presenter{

    private lateinit var mView: ListViewContract.View
    override val coroutineContext: CoroutineContext = Job() + Dispatchers.Main

    override fun takeView(view: ListViewContract.View) {
        mView = view
        start()
    }

    override fun start(){
        mView.showProgress()
        mView.showAllBooks(getAllData())
        mView.deleteProgress()
    }

    private fun getAllData():List<Book>{
        var bookList: List<Book>? = null
        launch {
            runCatching {
                bookList = BookRepository.getAllData().toList()
            }
        }

        if(bookList != null){
            return bookList!!
        }
        else{
            return emptyList()
        }
    }

}