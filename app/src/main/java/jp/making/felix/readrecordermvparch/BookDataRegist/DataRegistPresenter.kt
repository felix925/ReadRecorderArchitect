package jp.making.felix.readrecordermvparch.BookDataRegist

import jp.making.felix.readrecordermvparch.data.Model.BaseModel

private data class ErrorMessage(val message:String)

class DataRegistPresenter(val DataBase: BaseModel,
                          val mView: DataRegistContract.View): DataRegistContract.Presenter{
    private val ValidateError = ErrorMessage("正しい情報を入力してください")
    private val NotFoundError = ErrorMessage("本が見つかりませんでした")
    private val CantRegistError = ErrorMessage("本が登録できませんでした")

    init{
        mView.presenter = this
    }

    override fun start() {
        mView.showProgress()
        mView.deleteProgress()
    }

    override fun registData(isbn: String):Boolean{
        if(validationCheck(isbn)){
            val result = DataBase.registData(isbn,1)
            if (!result){
                mView.showToast(CantRegistError.message)
                return false
            }
            else{
                return true
            }
        }
        else{
            mView.showToast(ValidateError.message)
            return false
        }
    }
    //入力されたISBN番号が正しいのバリデーションチェック
    private fun validationCheck(isbn: String):Boolean {
        val regex = Regex(pattern = "[0-9]")
        return regex.matches(isbn)
//        DataBase.registData(name,imageURL)
    }
}