package jp.making.felix.readrecordermvparch.BookDataUpdate

import android.widget.Toast
import jp.making.felix.readrecordermvparch.Base.BasePresenter
import jp.making.felix.readrecordermvparch.Base.BaseView

interface DataUpdateContract {
    interface View: BaseView<Presenter> {
        fun showProgress()
        fun deleteProgress()
        fun showToast(text: String)
    }
    interface Presenter: BasePresenter {
        fun registData(id:String,page:String, thought:String)
    }
}