package jp.making.felix.readrecordermvparch.BookDataRegist

import jp.making.felix.readrecordermvparch.data.Model.BaseRepository

private data class ErrorMessage(val message:String)

class DataRegistPresenter(val BookRepository: BaseRepository,
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
//        if (isbn.isEmpty()){
//            mView.showEditError("ISBNを入力してください")
//        }
//        if(validationCheck(isbn)) {
//            GlobalScope.launch {
//                launch {
//                    DataBase.registData(isbn, 1)
//                }
//            }
//            return true
//        }
//        else{
//            mView.showToast(ValidateError.message)
            return false
//        }
    }
    //入力されたISBN番号が正しいのバリデーションチェック
    private fun validationCheck(isbn: String):Boolean {
        val regex = Regex(pattern = "[0-9]+")
        return regex.matches(isbn)
//        DataBase.registData(name,imageURL)
    }
}