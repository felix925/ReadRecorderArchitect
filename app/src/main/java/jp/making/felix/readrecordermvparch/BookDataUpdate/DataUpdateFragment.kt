package jp.making.felix.readrecordermvparch.BookDataUpdate

import android.content.Context
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
import jp.making.felix.readrecordermvparch.BookListView.MainActivity
import jp.making.felix.readrecordermvparch.DI.App
import jp.making.felix.readrecordermvparch.R
import kotlinx.android.synthetic.main.book_update_fragment.*
import javax.inject.Inject

class DataUpdateFragment : Fragment(), DataUpdateContract.View, BaseFragment {
    @Inject
    lateinit var presenter: DataUpdateContract.Presenter
    val args: DataViewFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        (activity!!.application as App).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        presenter.attachView(this)
        val view = inflater.inflate(R.layout.book_update_fragment,container,false)
        activity?.let {
            (activity as MainActivity).mainBinding.fab.setOnClickListener{
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

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView()
    }
}