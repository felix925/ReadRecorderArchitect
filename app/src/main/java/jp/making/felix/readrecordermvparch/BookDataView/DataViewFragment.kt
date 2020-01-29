package jp.making.felix.readrecordermvparch.BookDataView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import jp.making.felix.readrecordermvparch.Base.BaseFragment
import jp.making.felix.readrecordermvparch.R
import jp.making.felix.readrecordermvparch.data.Model.BookRepository
import jp.making.felix.readrecordermvparch.data.Model.LocalBookModel
import jp.making.felix.readrecordermvparch.data.Model.RemoteBookModel
import kotlin.reflect.jvm.internal.impl.descriptors.EffectiveVisibility

class DataViewFragment: Fragment(), DataViewContract.View, BaseFragment {
    override lateinit var presenter: DataViewContract.Presenter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.book_data_fragment,container,false)
        DataViewPresenter(BookRepository(LocalBookModel(),RemoteBookModel()),this)
        activity?.findViewById<FloatingActionButton>(R.id.fab)?.setOnClickListener{
            FabAction()
        }
        return view
    }

    override fun FabAction() {
    }

    override fun deleteProgress() {
    }

    override fun showProgress() {
    }

    override fun setUpButtonIcon() {
    }
}