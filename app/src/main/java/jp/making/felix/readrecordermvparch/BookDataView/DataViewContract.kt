package jp.making.felix.readrecordermvparch.BookDataView

import jp.making.felix.readrecordermvparch.Base.BasePresenter
import jp.making.felix.readrecordermvparch.Base.BaseView
import jp.making.felix.readrecordermvparch.data.Logs
import jp.making.felix.readrecordermvparch.data.Page

interface DataViewContract {
    interface View: BaseView<Presenter> {
        fun showProgress()
        fun deleteProgress()
    }

    interface Presenter: BasePresenter{
        fun getPageData(id:String):Pair<Array<Page>,Int>
        fun getThoughtData(id: String):Array<Logs>
        fun getBookId(id: String):String
    }
}