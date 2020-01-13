package jp.making.felix.readrecordermvparch.BookListView

import jp.making.felix.readrecordermvparch.Base.BasePresenter
import jp.making.felix.readrecordermvparch.Base.BaseView
import jp.making.felix.readrecordermvparch.data.Book

interface ListViewContract {
    interface View:BaseView<Presenter>{
        fun showProgress()
        fun deleteProgress()
        fun pressBooks(count:Int)
        fun showAllBooks(books:List<Book>)
    }

    interface Presenter:BasePresenter
}