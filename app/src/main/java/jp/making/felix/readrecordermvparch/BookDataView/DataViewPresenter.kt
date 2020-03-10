package jp.making.felix.readrecordermvparch.BookDataView

import jp.making.felix.readrecordermvparch.data.BookModel.Book
import jp.making.felix.readrecordermvparch.data.BookModel.Logs
import jp.making.felix.readrecordermvparch.data.Repository.BaseRepository
import jp.making.felix.readrecordermvparch.data.BookModel.Page
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.Exception
import kotlin.coroutines.CoroutineContext

class DataViewPresenter(val BookRepository: BaseRepository):DataViewContract.Presenter{
    var mView: DataViewContract.View? = null
    override val coroutineContext: CoroutineContext = Job() + Dispatchers.Main
    override fun takeView(view: DataViewContract.View) {
        mView = view
    }
    override fun start(){
        mView?.showProgress()
        mView?.deleteProgress()
    }
    override fun getPageData(id: String): Pair<Array<Page>, Int> {
        var books: Book = Book()
        var log:Array<Page>? = null
        var maxPage = 0
        launch {
            runCatching { books = BookRepository.searchData(id) }
                .onFailure { throw NoSuchFieldException("Book NotFound") }
                .onSuccess {
                    log = books.pages.toTypedArray()
                    maxPage = books.maxPage.toInt()
                }
        }
        if(log != null) {
            return Pair(log!!, maxPage)
        }
        else {
            throw Exception("Valid Error")
        }
    }

    override fun getThoughtData(id: String): Array<Logs> {
        var books:Book? = null
        launch {
            books = BookRepository.searchData(id)
        }
        if (books != null) {
            return books!!.readLog.toTypedArray()
        }
        else{
            throw Exception("Illegal touch")
        }
    }

    override fun getBookId(id: String):String{
        var books:List<Book>? = null
        launch {
            books = BookRepository.getAllData()
        }
        if (books != null) {
            return books!![id.toInt()].id
        }
        else{
            throw Exception("Illegal touch")
        }
    }
}