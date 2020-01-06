package jp.making.felix.readrecordermvparch.BookListView

import jp.making.felix.readrecordermvparch.data.Book
import jp.making.felix.readrecordermvparch.data.Dao.BaseDao

class ListViewPresenter(val DataBase:BaseDao,
                        val mView: ListViewContract.View):ListViewContract.Presenter{
    init{
        mView.presenter = this
    }
    override fun start(){
        mView.showProgress()
        mView.showAllBooks(getAllData())
        mView.deleteProgress()
    }

    fun getData(count: String):Book{
        return DataBase.searchData(count)
    }

    fun getAllData():List<Book>{
        return DataBase.getAllData().toList()
    }

}