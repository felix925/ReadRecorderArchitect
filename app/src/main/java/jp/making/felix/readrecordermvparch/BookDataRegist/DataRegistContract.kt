package jp.making.felix.readrecordermvparch.BookDataRegist

import jp.making.felix.readrecordermvparch.Base.BasePresenter

interface DataRegistContract {
    interface View {
        fun showProgress()
        fun deleteProgress()
        fun showToast(text: String)
        fun showEditError(text: String)
        fun showBook(thumbnail: String)
    }

    interface Presenter : BasePresenter<View> {
        fun searchBook(isbn: String)
        fun attachView(view: View)
    }
}