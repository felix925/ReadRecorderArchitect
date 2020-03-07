package jp.making.felix.readrecordermvparch.BookDataUpdate

import android.util.Log
import jp.making.felix.readrecordermvparch.data.Model.ModelContract

class DataUpdatePresenter (val BookRepository: ModelContract.Repository,
                           val mView: DataUpdateContract.View): DataUpdateContract.Presenter{
    init {
        mView.presenter = this
    }

    override fun registData(id:String,page: String, thought: String) {
        Log.i("update is called", "id = $id  page = $page thought =  $thought")
        if (BookRepository.searchData(id).pages.last()!!.pageData < page.toInt()){
            BookRepository.updateData(id,page,thought)
        }
        else{
            mView.showToast("前のページ数より大きなページ数を入力してください")
        }

    }

    override fun start() {
        mView.showProgress()
        mView.deleteProgress()
    }
}