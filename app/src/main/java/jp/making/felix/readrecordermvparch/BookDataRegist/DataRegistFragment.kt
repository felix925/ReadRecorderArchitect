package jp.making.felix.readrecordermvparch.BookDataRegist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import jp.making.felix.readrecordermvparch.DI.App
import jp.making.felix.readrecordermvparch.R
import jp.making.felix.readrecordermvparch.databinding.BookRegistFragmentBinding
import javax.inject.Inject

class DataRegistFragment: Fragment(), DataRegistContract.View {
    @Inject
    lateinit var presenter: DataRegistContract.Presenter
    private lateinit var binding: BookRegistFragmentBinding
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
        binding = BookRegistFragmentBinding.inflate(layoutInflater)
        val view = inflater.inflate(R.layout.book_regist_fragment,container,false)
        return view
    }

    override fun deleteProgress() {
    }

    override fun showProgress() {
    }

    override fun showToast(text: String) {
        Toast.makeText(context,text,Toast.LENGTH_LONG).show()
    }

    override fun showEditError(text: String) {
        binding.editText.error = text
    }
    private fun deleteEditError(){
        binding.editText.error = null
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView()
    }
}

