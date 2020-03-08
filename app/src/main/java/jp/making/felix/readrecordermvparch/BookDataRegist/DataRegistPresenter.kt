package jp.making.felix.readrecordermvparch.BookDataRegist

import jp.making.felix.readrecordermvparch.data.Model.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

private data class ErrorMessage(val message:String)

class DataRegistPresenter(val BookRepository: BaseRepository): DataRegistContract.Presenter{
    //TODO ここのnullをどうにかしたい(nullsafetyが活かせてない)
    private var mView:DataRegistContract.View? = null
    override val coroutineContext: CoroutineContext = Job() + Dispatchers.Main
    private val ValidateError = ErrorMessage("正しい情報を入力してください")
    private val NotFoundError = ErrorMessage("本がすでに登録されているか、見つかりませんでした")

    override fun takeView(view: DataRegistContract.View) {
        mView = view
    }

    override fun start() {
        mView?.showProgress()
        mView?.deleteProgress()
    }

    override fun registData(isbn: String):Boolean{
        if (isbn.isEmpty()){
            mView?.showEditError("ISBNを入力してください")
        }
        if(validationCheck(isbn)) {
            var flag:Boolean = false
            launch {
                mView?.showProgress()
                runCatching { BookRepository.registData(isbn, 0) }
                    .onSuccess { flag = true }
                    .onFailure {
                        mView?.showToast(NotFoundError.message)
                        flag = false
                    }
                mView?.deleteProgress()
            }
            return flag
        }
        else{
            mView?.showToast(ValidateError.message)
            return false
        }
    }

    override fun loadBooks() {

    }
    //入力されたISBN番号が正しいのバリデーションチェック
    private fun validationCheck(isbn: String):Boolean {
        val regex = Regex(pattern = "[0-9]+")
        return regex.matches(isbn)
    }
}