package jp.making.felix.readrecordermvparch.BookDataUpdate

import jp.making.felix.readrecordermvparch.Base.BaseFragment
import jp.making.felix.readrecordermvparch.Base.BasePresenter

interface DataUpdateContract {
    interface View:BaseFragment{
        var presenter: Presenter
        fun showProgress()
        fun deleteProgress()
        fun showToast(text:String)
    }
    interface Presenter: BasePresenter<View>{
        fun updateData(id:String,page: String, thought: String)
    }
}