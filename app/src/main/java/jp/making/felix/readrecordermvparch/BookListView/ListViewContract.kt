package jp.making.felix.readrecordermvparch.BookListView

import jp.making.felix.readrecordermvparch.Base.BasePresenter
import jp.making.felix.readrecordermvparch.data.BookModel.Book

interface ListViewContract {
    interface View{
        fun showProgress()
        fun deleteProgress()
        fun pressBooks(result: String)
        fun showBooks(books:List<Book>)
    }

    interface Presenter:BasePresenter<View>{
        fun attachView(view: View)
    }
}