package jp.making.felix.readrecordermvparch.BookListView

import jp.making.felix.readrecordermvparch.Base.BasePresenter
import jp.making.felix.readrecordermvparch.Base.BaseView
import jp.making.felix.readrecordermvparch.data.Book

interface BookListContract {
    interface View:BaseView<Presenter>{
        fun showProgress()
        fun deleteProgress()
        fun showBooks(count:Int)
        fun showAllBooks(books:List<Book>)
    }

    interface Presenter:BasePresenter
}