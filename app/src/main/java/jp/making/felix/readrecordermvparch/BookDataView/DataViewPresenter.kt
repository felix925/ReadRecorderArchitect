package jp.making.felix.readrecordermvparch.BookDataView

import jp.making.felix.readrecordermvparch.data.BookModel.Book
import jp.making.felix.readrecordermvparch.data.BookModel.Logs
import jp.making.felix.readrecordermvparch.data.BookModel.Page
import jp.making.felix.readrecordermvparch.data.Repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DataViewPresenter(val BookRepository: BaseRepository):DataViewContract.Presenter{
    override val coroutineContext: CoroutineContext = Job() + Dispatchers.Main
    private var mView:DataViewContract.View? = null

    override fun attachView(view: DataViewContract.View) {
        mView = view
    }

    override fun start():Job = launch {
        mView?.showProgress()
        mView?.deleteProgress()
    }

    override fun getPageData(id: String): Pair<Array<Page>, Int> {
        lateinit var books: Book
        launch {
            runCatching {
                books = BookRepository.searchData(id)
            }
        }
        val log = books.pages.toTypedArray()
        val maxPage = books.maxPage.toInt()
        return Pair(log,maxPage)
    }

    override fun getThoughtData(id: String): Array<Logs> {
        lateinit var books :Book
        launch {
            runCatching {
                books = BookRepository.searchData(id)
            }
        }
        return books.readLog.toTypedArray()
    }

    override fun getBookId(id: String):String{
        lateinit var books:List<Book>
        launch {
            runCatching {
                books = BookRepository.getAllData()
            }
        }
        return books[id.toInt()].id
    }
}