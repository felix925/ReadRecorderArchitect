package jp.making.felix.readrecordermvparch.BookListView

import jp.making.felix.readrecordermvparch.DI.FragmentScope
import jp.making.felix.readrecordermvparch.data.Repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@FragmentScope
class ListViewPresenter @Inject constructor(
    private val BookRepository: BaseRepository
) : ListViewContract.Presenter {

    private var mView: ListViewContract.View? = null
    override val coroutineContext: CoroutineContext = Job() + Dispatchers.Main

    override fun attachView(view: ListViewContract.View) {
        mView = view
    }

    override fun start() {
        launch {
            mView?.showProgress()
            mView?.showBooks(BookRepository.getAllData())
            mView?.deleteProgress()
        }
    }

    override fun dropView() {
        super.dropView()
        mView = null
    }
}