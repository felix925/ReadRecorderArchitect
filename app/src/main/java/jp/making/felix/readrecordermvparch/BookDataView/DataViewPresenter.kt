package jp.making.felix.readrecordermvparch.BookDataView

import android.util.Log
import jp.making.felix.readrecordermvparch.data.Book
import jp.making.felix.readrecordermvparch.data.Logs
import jp.making.felix.readrecordermvparch.data.Model.BaseRepository
import jp.making.felix.readrecordermvparch.data.Page

class DataViewPresenter(val BookRepository: BaseRepository,
                        val mView: DataViewContract.View):DataViewContract.Presenter{
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