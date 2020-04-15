package jp.making.felix.readrecordermvparch.BookDataUpdate

import jp.making.felix.readrecordermvparch.Base.BasePresenter

interface DataUpdateContract {
    interface View {
        fun showProgress()
        fun deleteProgress()
        fun showToast(text: String)
        fun showMaxPage(maxPage: Int)
    }

    interface Presenter : BasePresenter<View> {
        fun updateData(id: String, page: String, thought: String)
        fun attachView(view: View)
        fun getMaxPage(id: String)
    }
}