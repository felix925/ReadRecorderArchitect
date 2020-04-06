package jp.making.felix.readrecordermvparch.BookDataRegist

import android.util.Log
import jp.making.felix.readrecordermvparch.DI.FragmentScope
import jp.making.felix.readrecordermvparch.data.Repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

private data class ErrorMessage(val message:String)

@FragmentScope
class DataRegistPresenter(val BookRepository: BaseRepository): DataRegistContract.Presenter{
    private var mView: DataRegistContract.View? = null
    private val ValidateError = ErrorMessage("正しい情報を入力してください")
    private val NotFoundError = ErrorMessage("本がすでに登録されているか、見つかりませんでした")
    private val SuccessMessage = "成功しました"
    override val coroutineContext: CoroutineContext = Job() + Dispatchers.Main
    override fun attachView(view: DataRegistContract.View) {
        mView= view
    }
    override fun start(){
        launch {
            mView?.showProgress()
            mView?.deleteProgress()
        }
    }

    override fun registData(isbn: String){
        if (isbn.isEmpty()){
            mView?.showEditError("ISBNを入力してください")
        }
        if(validationCheck(isbn)) {
            launch {
                runCatching {
                    BookRepository.registData(isbn,0)
                }
            }
        }
        else{
            mView?.showToast(ValidateError.message)
        }
    }
    //入力されたISBN番号が正しいのバリデーションチェック
    private fun validationCheck(isbn: String):Boolean {
        val regex = Regex(pattern = "[0-9]+")
        return regex.matches(isbn)
    }
}