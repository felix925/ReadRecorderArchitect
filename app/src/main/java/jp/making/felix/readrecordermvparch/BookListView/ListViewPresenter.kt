package jp.making.felix.readrecordermvparch.BookListView

import jp.making.felix.readrecordermvparch.data.Book
import jp.making.felix.readrecordermvparch.data.Model.BaseRepository
import kotlinx.coroutines.*

class ListViewPresenter(val BookRepository:BaseRepository,
                        val mView: ListViewContract.View):ListViewContract.Presenter{
    init{
        mView.presenter = this
    }
    override fun start(){
        mView.showProgress()
        mView.showAllBooks(getAllData())
        mView.deleteProgress()
    }

    fun getAllData():List<Book>{
        return BookRepository.getAllData().toList()
    }

}