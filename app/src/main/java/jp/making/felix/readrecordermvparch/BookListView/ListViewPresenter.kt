package jp.making.felix.readrecordermvparch.BookListView

import jp.making.felix.readrecordermvparch.data.Book
import jp.making.felix.readrecordermvparch.data.Model.BaseModel

class ListViewPresenter(val DataBase:BaseModel,
                        val mView: ListViewContract.View):ListViewContract.Presenter{
    init{
        mView.presenter = this
    }
    override fun start(){
        mView.showProgress()
        mView.showAllBooks(getAllData())
        mView.deleteProgress()
    }

    fun getAllData():List<Book>{
        return DataBase.getAllData().toList()
    }

}