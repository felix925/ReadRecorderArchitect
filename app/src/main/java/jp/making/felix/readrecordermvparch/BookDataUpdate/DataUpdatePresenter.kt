package jp.making.felix.readrecordermvparch.BookDataUpdate

import jp.making.felix.readrecordermvparch.data.Repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DataUpdatePresenter (val BookRepository: BaseRepository): DataUpdateContract.Presenter{
    var mView: DataUpdateContract.View? = null
    override val coroutineContext: CoroutineContext = Job() + Dispatchers.Main

    override fun takeView(view: DataUpdateContract.View) {
        mView = view
    }

    override fun updateData(id:String,page: String, thought: String){
        var flag = false
        launch {
            if (BookRepository.searchData(id).pages.last()!!.pageData < page.toInt()){
                flag = BookRepository.updateData(id,page,thought)
            }
            else{
                mView?.showToast("前のページ数より大きなページ数を入力してください")
            }
        }
        if (!flag) mView?.showToast("更新に失敗しました")
    }

    override fun start() {
        mView?.showProgress()
        mView?.deleteProgress()
    }
}