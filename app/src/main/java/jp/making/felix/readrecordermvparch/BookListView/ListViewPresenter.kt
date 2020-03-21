package jp.making.felix.readrecordermvparch.BookListView

import jp.making.felix.readrecordermvparch.data.BookModel.Book
import jp.making.felix.readrecordermvparch.data.Repository.BaseRepository
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ListViewPresenter @Inject constructor(
    val BookRepository: BaseRepository
):ListViewContract.Presenter{

    private var mView: ListViewContract.View? = null
    override val coroutineContext: CoroutineContext = Job() + Dispatchers.Main

    override fun attachView(view:ListViewContract.View){
        mView = view
    }

    override fun start() {
        launch {
            mView?.showProgress()
            mView?.showAllBooks(BookRepository.getAllData())
            mView?.deleteProgress()
        }
    }

    override fun dropView() {
        super.dropView()
        mView = null
    }
}