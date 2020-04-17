package jp.making.felix.readrecordermvparch.BookDataRegist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.api.load
import jp.making.felix.readrecordermvparch.DI.App
import jp.making.felix.readrecordermvparch.R
import jp.making.felix.readrecordermvparch.databinding.BookRegistFragmentBinding
import javax.inject.Inject

class DataRegistFragment : Fragment(R.layout.book_regist_fragment), DataRegistContract.View {
    @Inject
    lateinit var presenter: DataRegistContract.Presenter
    private lateinit var binding: BookRegistFragmentBinding
    override fun onAttach(context: Context) {
        (activity!!.application as App).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        binding = BookRegistFragmentBinding.bind(view)
        binding.registButton.setOnClickListener {
            presenter.searchBook(binding.registEditText.text.toString())
            it.isActivated = false
        }
    }

    override fun deleteProgress() {
    }

    override fun showProgress() {
    }

    override fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }

    override fun showEditError(text: String) {
        binding.registEditText.error = text
    }

    private fun deleteEditError() {
        binding.registEditText.error = null
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView()
    }

    override fun showBook(thumbnail: String) {
        binding.registButton.isVisible = false
        binding.registEditText.isVisible = false
        binding.registThumbnail.load(thumbnail)
    }
}

