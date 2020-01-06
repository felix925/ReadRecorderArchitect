package jp.making.felix.readrecordermvparch.BookDataView

import jp.making.felix.readrecordermvparch.data.Book
import jp.making.felix.readrecordermvparch.data.Dao.BaseDao

class DataViewPresenter(val DataBase: BaseDao,
                        val mView: DataViewContract.View):DataViewContract.Presenter{
    init{
        mView.presenter = this
    }
    override fun start(){
        mView.showProgress()
        mView.deleteProgress()
    }

    fun getData(count: String): Book {
        return DataBase.searchData(count)
    }
}