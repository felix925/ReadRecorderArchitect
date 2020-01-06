package jp.making.felix.readrecordermvparch.BookDataView

import jp.making.felix.readrecordermvparch.data.Book
import jp.making.felix.readrecordermvparch.data.Dao.BaseDao

class DataViewPresenter(val DataBase: BaseDao, val mView: DataViewContract.View):DataViewContract.Presenter{
    init{
        mView.presenter = this
    }
    override fun start(){
        mView.showProgress()
        mView.deleteProgress()
    }

    override fun getData(count: String): Book {
        return DataBase.searchData(count)
    }

    override fun getAllData():List<Book>{
        return DataBase.getAllData().toList()
    }

    override fun registData(name: String, imageURL: String) {
        DataBase.registData(name,imageURL)
    }
}