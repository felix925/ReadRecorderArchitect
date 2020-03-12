package jp.making.felix.readrecordermvparch.BookDataUpdate

import android.util.Log
import jp.making.felix.readrecordermvparch.data.Model.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class DataUpdatePresenter (val BookRepository: BaseRepository): DataUpdateContract.Presenter{
    private var mView: DataUpdateContract.View? = null
    override val coroutineContext: CoroutineContext = Job() + Dispatchers.Main
    override fun attachView(view: DataUpdateContract.View) {
        mView = view
    }
    override fun updateData(id:String,page: String, thought: String) {
        Log.i("update is called", "id = $id  page = $page thought =  $thought")
        if (BookRepository.searchData(id).pages.last()!!.pageData < page.toInt()){
            BookRepository.updateData(id,page,thought)
        }
        else{
            mView?.showToast("前のページ数より大きなページ数を入力してください")
        }

    }

    override fun start() {
        mView?.showProgress()
        mView?.deleteProgress()
    }
}