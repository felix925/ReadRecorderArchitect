package jp.making.felix.readrecordermvparch.BookDataRegist

import jp.making.felix.readrecordermvparch.Base.BaseFragment
import jp.making.felix.readrecordermvparch.Base.BasePresenter

interface DataRegistContract {
    interface View:BaseFragment{
        fun showProgress()
        fun deleteProgress()
        fun showToast(text:String)
        fun showEditError(text:String)
    }
    interface Presenter: BasePresenter<View>{
        fun registData(isbn:String):Boolean
        fun attachView(view:View)
    }
}