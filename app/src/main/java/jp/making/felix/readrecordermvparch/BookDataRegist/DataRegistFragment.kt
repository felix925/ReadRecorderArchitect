package jp.making.felix.readrecordermvparch.BookDataRegist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import jp.making.felix.readrecordermvparch.DI.App
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
        binding = BookRegistFragmentBinding.inflate(inflater, container, false)
        binding.registButton.setOnClickListener {
            presenter.searchBook(binding.registEditText.text.toString())
        }
        binding.acceptButton.setOnClickListener {
            presenter.registBook()
            it.isVisible = false
        }
        return binding.root
    }

    override fun deleteProgress() {
    }

    override fun showProgress() {
    }

    override fun showToast(text: String) {
        Toast.makeText(context,text,Toast.LENGTH_LONG).show()
    }

    override fun showEditError(text: String) {
        binding.registEditText.error = text
    }
    private fun deleteEditError(){
        binding.registEditText.error = null
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView()
    }

    override fun showBook(thumbnail: String, title:String) {
        Picasso.get().load(thumbnail).into(binding.registThumbnail)
        binding.bookTitle.text = title
        binding.registButton.isVisible = false
        binding.acceptButton.isVisible = true
        binding.registEditText.isVisible = false
    }
}

