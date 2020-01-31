package jp.making.felix.readrecordermvparch.BookDataRegist

import jp.making.felix.readrecordermvparch.data.Model.BaseRepository
import jp.making.felix.readrecordermvparch.data.Model.ModelContract
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private data class ErrorMessage(val message:String)

class DataRegistPresenter(val BookRepository: ModelContract.Repository,
                          val mView: DataRegistContract.View): DataRegistContract.Presenter{
    private val ValidateError = ErrorMessage("正しい情報を入力してください")
    private val NotFoundError = ErrorMessage("本がすでに登録されているか、見つかりませんでした")

    init{
        mView.presenter = this
    }

    override fun start() {
        mView.showProgress()
        mView.deleteProgress()
    }

    override fun registData(isbn: String):Boolean{
        if (isbn.isEmpty()){
            mView.showEditError("ISBNを入力してください")
        }
        if(validationCheck(isbn)) {
            val flag = BookRepository.registData(isbn,0)
            if(!flag){
                mView.showToast(NotFoundError.message)
            }
            return flag
        }
        else{
            mView.showToast(ValidateError.message)
            return false
         }
    }
    //入力されたISBN番号が正しいのバリデーションチェック
    private fun validationCheck(isbn: String):Boolean {
        val regex = Regex(pattern = "[0-9]+")
        return regex.matches(isbn)
    }
}