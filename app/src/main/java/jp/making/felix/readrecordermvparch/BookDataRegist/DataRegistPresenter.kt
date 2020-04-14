package jp.making.felix.readrecordermvparch.BookDataRegist

import jp.making.felix.readrecordermvparch.DI.FragmentScope
import jp.making.felix.readrecordermvparch.data.BookModel.Book
import jp.making.felix.readrecordermvparch.data.Repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

private data class ErrorMessage(val message: String)

@FragmentScope
class DataRegistPresenter(val BookRepository: BaseRepository) : DataRegistContract.Presenter {
    private var mView: DataRegistContract.View? = null
    private val ValidateError = ErrorMessage("正しい情報を入力してください")
    override val coroutineContext: CoroutineContext = Job() + Dispatchers.Main

    override fun attachView(view: DataRegistContract.View) {
        mView = view
    }

    override fun start() {
        mView?.showProgress()
        mView?.deleteProgress()
    }

    override fun searchBook(isbn: String) {
        lateinit var result: Book
        if (isbn.isEmpty()) {
            mView?.showEditError("ISBNを入力してください")
        }
        if (validationCheck(isbn) && isbn.length == 13) {
            launch {
                async {
                    result = BookRepository.searchBook(isbn, 0)
                }.await()
                mView?.showBook(result)
            }
        } else {
            mView?.showToast(ValidateError.message)
        }
    }

    override fun registBook(book: Book) {
        launch {
            runCatching {
                BookRepository.registBook(book)
            }
        }
    }

    //入力されたISBN番号が正しいのバリデーションチェック
    private fun validationCheck(isbn: String): Boolean {
        val regex = Regex(pattern = "[0-9]+")
        return regex.matches(isbn)
    }
}