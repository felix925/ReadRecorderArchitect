package jp.making.felix.readrecordermvparch.BookListView

import jp.making.felix.readrecordermvparch.data.Book
import jp.making.felix.readrecordermvparch.data.Dao.BaseDao

class BookListViewPresenter(val DataBase:BaseDao,val mView: BookListContract.View):BookListContract.Presenter{
    init{
        mView.presenter = this
    }
    override fun start(){
        mView.showProgress()
        mView.showAllBooks(getAllData())
        mView.deleteProgress()
    }

    override fun getData(count: String):Book{
        return DataBase.searchData(count)
    }

    override fun getAllData():List<Book>{
        return DataBase.getAllData().toList()
    }

    override fun registData(name: String, imageURL: String) {
        DataBase.registData(name,imageURL)
        mView.showAllBooks(getAllData())
    }
}