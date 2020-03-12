package jp.making.felix.readrecordermvparch.BookDataView

import jp.making.felix.readrecordermvparch.Base.BasePresenter
import jp.making.felix.readrecordermvparch.BookListView.ListViewContract
import jp.making.felix.readrecordermvparch.data.BookModel.Logs
import jp.making.felix.readrecordermvparch.data.BookModel.Page

interface DataViewContract {
    interface View{
        fun showProgress()
        fun deleteProgress()
    }
    interface Presenter: BasePresenter<View>{
        fun getPageData(id: String): Pair<Array<Page>, Int>
        fun getThoughtData(id: String): Array<Logs>
        fun getBookId(id: String):String
        fun attachView(view: View)
    }
}