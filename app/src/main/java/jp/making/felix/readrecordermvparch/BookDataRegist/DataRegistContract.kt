package jp.making.felix.readrecordermvparch.BookDataRegist

import jp.making.felix.readrecordermvparch.Base.BasePresenter
import jp.making.felix.readrecordermvparch.Base.BaseView

interface DataRegistContract {
    interface View: BaseView<Presenter> {
        fun showProgress()
        fun deleteProgress()
        fun showToast(text:String)
        fun showEditError(text:String)
    }
    interface Presenter: BasePresenter{
        fun registData(isbn:String):Boolean
    }
}