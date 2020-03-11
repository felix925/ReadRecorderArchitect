package jp.making.felix.readrecordermvparch.BookListView

import jp.making.felix.readrecordermvparch.Base.BasePresenter
import jp.making.felix.readrecordermvparch.data.BookModel.Book

interface ListViewContract {
    interface View{
        var presenter: Presenter
        fun showProgress()
        fun deleteProgress()
        fun pressBooks(count:Int)
        fun showAllBooks(books:List<Book>)
    }

    interface Presenter:BasePresenter<View>
}