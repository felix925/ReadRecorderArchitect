package jp.making.felix.readrecordermvparch.BookDataUpdate

import jp.making.felix.readrecordermvparch.Base.BaseFragment
import jp.making.felix.readrecordermvparch.Base.BasePresenter
import jp.making.felix.readrecordermvparch.BookDataView.DataViewContract

interface DataUpdateContract {
    interface View:BaseFragment{
        fun showProgress()
        fun deleteProgress()
        fun showToast(text:String)
    }
    interface Presenter: BasePresenter<View>{
        fun updateData(id:String,page: String, thought: String)
        fun attachView(view: View)
    }
}