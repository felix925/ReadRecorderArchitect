package jp.making.felix.readrecordermvparch.BookDataRegist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import jp.making.felix.readrecordermvparch.Base.BaseFragment
import jp.making.felix.readrecordermvparch.DI.App
import jp.making.felix.readrecordermvparch.R
import javax.inject.Inject

class DataRegistFragment: Fragment(), DataRegistContract.View, BaseFragment{
    @Inject
    lateinit var presenter: DataRegistContract.Presenter
//    private lateinit var binding: BookRegistFragmentBinding
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
//        binding = BookRegistFragmentBinding.inflate(layoutInflater)
        val view = inflater.inflate(R.layout.book_regist_fragment,container,false)
        activity?.let {
            it.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener{
                FabAction()
            }
        }
        return view
    }

    override fun FabAction() {
        activity?.findViewById<EditText>(R.id.editText)?.let{
            it.error?.apply {
                deleteEditError()
            }
            presenter.registData(it.text.toString())
            findNavController().popBackStack()
        }
    }

    override fun deleteProgress() {
        activity?.findViewById<ProgressBar>(R.id.mainProgress)?.let{
            it.visibility = ProgressBar.INVISIBLE
        }
    }

    override fun showProgress() {
        activity?.findViewById<ProgressBar>(R.id.mainProgress)?.let{
            it.visibility = ProgressBar.VISIBLE
        }
    }

    override fun showToast(text: String) {
        Toast.makeText(context,text,Toast.LENGTH_LONG).show()
    }

    override fun setUpButtonIcon() {
        activity?.findViewById<FloatingActionButton>(R.id.fab)?.setImageDrawable(resources.getDrawable(R.drawable.filter))
    }

    override fun showEditError(text: String) {
        activity?.findViewById<EditText>(R.id.editText)?.error = text
    }
    private fun deleteEditError(){
        activity?.findViewById<EditText>(R.id.editText)?.error = null
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView()
    }
}

