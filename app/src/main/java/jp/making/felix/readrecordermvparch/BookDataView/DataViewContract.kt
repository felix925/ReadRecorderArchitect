package jp.making.felix.readrecordermvparch.BookDataView

import jp.making.felix.readrecordermvparch.Base.BasePresenter
import jp.making.felix.readrecordermvparch.data.BookModel.Logs
import jp.making.felix.readrecordermvparch.data.BookModel.Page

interface DataViewContract {
    interface View{
        fun showProgress()
        fun deleteProgress()
        fun setUpChart(bookId: String, pageData: Pair<Array<Page>, Int>)
        fun setUpThought(bookId:String, logList: List<Logs>, pageList: List<Page>)
        fun navigationTrigger(id: String)
    }
    interface Presenter: BasePresenter<View>{
        fun attachView(view: View)
        fun setUpChartAndList(bookId: String)
        fun navigateTrigger(id: String)
    }
}