package jp.making.felix.readrecordermvparch.BookDataRegist

import jp.making.felix.readrecordermvparch.Base.BasePresenter

interface DataRegistContract {
    interface View{
        fun showProgress()
        fun deleteProgress()
        fun showToast(text:String)
        fun showEditError(text:String)
    }
    interface Presenter: BasePresenter<View>{
        fun loadBooks()
        fun registData(isbn:String):Boolean
    }
}