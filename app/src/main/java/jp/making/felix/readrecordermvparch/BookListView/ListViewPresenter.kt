package jp.making.felix.readrecordermvparch.BookListView

import jp.making.felix.readrecordermvparch.data.BookModel.Book
import jp.making.felix.readrecordermvparch.data.Model.BaseRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ListViewPresenter(val BookRepository:BaseRepository,
                        val mView: ListViewContract.View):ListViewContract.Presenter{
    override val coroutineContext: CoroutineContext = Job() + Dispatchers.Main
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