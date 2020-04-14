package jp.making.felix.readrecordermvparch.BookDataRegist

import jp.making.felix.readrecordermvparch.Base.BasePresenter
import jp.making.felix.readrecordermvparch.data.BookModel.Book

interface DataRegistContract {
    interface View {
        fun showProgress()
        fun deleteProgress()
        fun showToast(text: String)
        fun showEditError(text: String)
        fun showBook(book: Book)
    }

    interface Presenter : BasePresenter<View> {
        fun searchBook(isbn: String)
        fun registBook(book: Book)
        fun attachView(view: View)
    }
}