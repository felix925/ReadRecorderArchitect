package jp.making.felix.readrecordermvparch.BookListView

import jp.making.felix.readrecordermvparch.data.BookModel.Book
import jp.making.felix.readrecordermvparch.data.Model.BaseRepository
import jp.making.felix.readrecordermvparch.data.Model.BookRepository
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ListViewPresenter @Inject constructor(
    val BookRepository:BookRepository
):ListViewContract.Presenter{

    private var mView: ListViewContract.View? = null
    override val coroutineContext: CoroutineContext = Job() + Dispatchers.Main

//    init{
//        mView?.presenter = this
//    }

    override fun attachView(view:ListViewContract.View){
        mView = view
    }

    override fun start(){
        mView?.showProgress()
        mView?.showAllBooks(getAllData())
        mView?.deleteProgress()
    }

    fun getAllData():List<Book>{
        return BookRepository.getAllData().toList()
    }

}