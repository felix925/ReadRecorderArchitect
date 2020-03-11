package jp.making.felix.readrecordermvparch.BookDataUpdate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import jp.making.felix.readrecordermvparch.Base.BaseFragment
import jp.making.felix.readrecordermvparch.BookDataView.DataViewFragmentArgs
import jp.making.felix.readrecordermvparch.R
import jp.making.felix.readrecordermvparch.data.Model.BookRepository
import kotlinx.android.synthetic.main.book_update_fragment.*

class DataUpdateFragment : Fragment(), DataUpdateContract.View, BaseFragment {
    override lateinit var presenter: DataUpdateContract.Presenter
    val args: DataViewFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    val view = inflater.inflate(R.layout.book_update_fragment,container,false)
    DataUpdatePresenter(BookRepository(),this)
    activity?.findViewById<FloatingActionButton>(R.id.fab)?.let {
        it.setOnClickListener {
            FabAction()
        }
    }
    return view
}
    override fun deleteProgress() {

    }

    override fun showProgress() {
    }

    override fun FabAction() {
        activity?.apply {
            val page = pageInput.text.toString()
            val thought = thoughtInput.text.toString()
            if(page.isNotEmpty()) {
                presenter.updateData(args.BOOKID, page, thought)
            }
            findNavController().popBackStack()
        }

    }

    override fun setUpButtonIcon() {

    }

    override fun showToast(text: String) {
        Toast.makeText(context,text, Toast.LENGTH_LONG).show()
    }
}