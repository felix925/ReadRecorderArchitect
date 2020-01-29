package jp.making.felix.readrecordermvparch.BookDataView

import jp.making.felix.readrecordermvparch.data.Book
import jp.making.felix.readrecordermvparch.data.Model.BaseRepository

class DataViewPresenter(val BookRepository: BaseRepository,
                        val mView: DataViewContract.View):DataViewContract.Presenter{
    init{
        mView.presenter = this
    }
    override fun start(){
        mView.showProgress()
        mView.deleteProgress()
    }
    //TODO getDataの実装
    fun getData(count: String): Book {
        return Book()
    }
}