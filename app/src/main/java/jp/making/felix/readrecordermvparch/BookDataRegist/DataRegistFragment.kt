package jp.making.felix.readrecordermvparch.BookDataRegist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import jp.making.felix.readrecordermvparch.Base.BaseFragment
import jp.making.felix.readrecordermvparch.BookDataView.DataViewContract
import jp.making.felix.readrecordermvparch.BookDataView.DataViewPresenter
import jp.making.felix.readrecordermvparch.R
import jp.making.felix.readrecordermvparch.data.Dao.BookDao

class DataRegistFragment: Fragment(), DataViewContract.View, BaseFragment {
    override lateinit var presenter: DataViewContract.Presenter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.book_list_fragment,container,false)
        DataViewPresenter(BookDao(),this)
        return view
    }

    override fun FabAction() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showProgress() {

    }
    fun registData(name: String, imageURL: String) {
//        DataBase.registData(name,imageURL)
//        mView.showAllBooks(getAllData())
    }
}
