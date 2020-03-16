package jp.making.felix.readrecordermvparch.BookDataRegist

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
    override val coroutineContext: CoroutineContext = Job() + Dispatchers.Main
    override fun attachView(view: DataRegistContract.View) {
        mView= view
    }
    override fun start():Job = launch {
        mView?.showProgress()
        mView?.deleteProgress()
    }

    override fun registData(isbn: String):Boolean{
        if (isbn.isEmpty()){
            mView?.showEditError("ISBNを入力してください")
        }
        if(validationCheck(isbn)) {
            var flag = false
            launch {
                runCatching {
                    flag = BookRepository.registData(isbn,0)
                }
            }
            if(!flag){
                mView?.showToast(NotFoundError.message)
                return false
            }
            return flag
        }
        else{
            mView?.showToast(ValidateError.message)
            return false
         }
    }
    //入力されたISBN番号が正しいのバリデーションチェック
    private fun validationCheck(isbn: String):Boolean {
        val regex = Regex(pattern = "[0-9]+")
        return regex.matches(isbn)
    }
}