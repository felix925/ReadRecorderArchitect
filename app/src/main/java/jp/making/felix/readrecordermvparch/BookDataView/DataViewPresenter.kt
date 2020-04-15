package jp.making.felix.readrecordermvparch.BookDataView

import jp.making.felix.readrecordermvparch.data.Repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class DataViewPresenter @Inject constructor(
    val BookRepository: BaseRepository
) : DataViewContract.Presenter {

    override val coroutineContext: CoroutineContext = Job() + Dispatchers.Main
    private var mView: DataViewContract.View? = null

    override fun attachView(view: DataViewContract.View) {
        mView = view
    }

    override fun start() {
        launch {
            mView?.showProgress()
            mView?.deleteProgress()
        }
    }

    override fun navigateTrigger(id: String) {
        launch {
            runCatching {
                BookRepository.searchDataById(id)
            }
                .onSuccess { mView?.navigationTrigger(it.id) }
                .onFailure { }
        }
    }

    override fun setUpChartAndList(bookId: String) {
        launch {
            BookRepository.searchDataById(bookId).let {
                mView?.setUpThought(it.id, it.readLog, it.pages)
                mView?.setUpChart(it.id, Pair(it.pages.toTypedArray(), it.maxPage.toInt()))
            }
        }
    }
}