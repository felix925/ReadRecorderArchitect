package jp.making.felix.readrecordermvparch.BookDataUpdate

import jp.making.felix.readrecordermvparch.data.Repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class DataUpdatePresenter @Inject constructor(
    val BookRepository: BaseRepository
) : DataUpdateContract.Presenter {

    private var mView: DataUpdateContract.View? = null
    override val coroutineContext: CoroutineContext = Job() + Dispatchers.Main
    override fun attachView(view: DataUpdateContract.View) {
        mView = view
    }

    override fun updateData(id: String, page: String, thought: String) {
        launch {
            runCatching {
                BookRepository.searchDataById(id)
            }
                .onSuccess {
                    if (it.pages.last()!!.pageData < page.toInt() && it.maxPage.toInt() >= page.toInt()) {
                        BookRepository.updateData(id, page, thought)
                        if (it.maxPage.toInt() == page.toInt()) {
                            BookRepository.updateReadFlag(it.id)
                        }
                    } else {
                        mView?.showToast("前のページ数より大きなページ数を入力してください")
                    }
                }
        }
    }

    override fun getMaxPage(id: String) {
        launch {
            var maxPage = 0
            async {
                maxPage = BookRepository.searchDataById(id).maxPage.toInt()
            }.await()
            mView?.showMaxPage(maxPage)
        }
    }

    override fun start() {
        launch {
            mView?.showProgress()
            mView?.deleteProgress()
        }
    }
}