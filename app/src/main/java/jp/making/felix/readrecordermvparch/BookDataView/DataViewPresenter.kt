package jp.making.felix.readrecordermvparch.BookDataView

import android.util.Log
import jp.making.felix.readrecordermvparch.data.BookModel.Logs
import jp.making.felix.readrecordermvparch.data.BookModel.Page
import jp.making.felix.readrecordermvparch.data.Model.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class DataViewPresenter(val BookRepository: BaseRepository,
                        val mView: DataViewContract.View):DataViewContract.Presenter{
    override val coroutineContext: CoroutineContext = Job() + Dispatchers.Main
    init{
        mView.presenter = this
    }

    override fun start(){
        mView.showProgress()
        mView.deleteProgress()
    }
    override fun getPageData(id: String): Pair<Array<Page>, Int> {
        val books = BookRepository.searchData(id)
        Log.i("books",books.toString())
        val log = books.pages.toTypedArray()
        val maxPage = books.maxPage.toInt()
        return Pair(log,maxPage)
    }

    override fun getThoughtData(id: String): Array<Logs> {
        val books = BookRepository.searchData(id)
        return books.readLog.toTypedArray()
    }

    override fun getBookId(id: String):String{
        val books = BookRepository.getAllData()
        return books[id.toInt()].id
    }
}