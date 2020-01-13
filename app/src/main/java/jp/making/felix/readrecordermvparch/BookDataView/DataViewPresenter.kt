package jp.making.felix.readrecordermvparch.BookDataView

import jp.making.felix.readrecordermvparch.data.Book
import jp.making.felix.readrecordermvparch.data.Dao.BaseModel

class DataViewPresenter(val DataBase: BaseModel,
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