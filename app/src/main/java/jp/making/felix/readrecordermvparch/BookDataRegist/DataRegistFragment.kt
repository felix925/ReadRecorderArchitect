package jp.making.felix.readrecordermvparch.BookDataRegist

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
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
import jp.making.felix.readrecordermvparch.data.BookModel.Book
import jp.making.felix.readrecordermvparch.databinding.BookRegistFragmentBinding
import jp.making.felix.readrecordermvparch.databinding.DialogRegistBookBinding
import javax.inject.Inject

class DataRegistFragment : Fragment(), DataRegistContract.View {
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
            it.isActivated = false
        }
        return binding.root
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

    override fun showBook(book: Book) {
        binding.registButton.isVisible = false
        binding.registEditText.isVisible = false
        activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = layoutInflater
            builder.apply {
                val dialogBinding = DialogRegistBookBinding.inflate(inflater)
                setView(dialogBinding.root)
                dialogBinding.dialogImage.load(book.imageUrl) {
                    error(R.drawable.ic_settings)
                }
                dialogBinding.dialogTitle.text = book.name
                setPositiveButton(
                    "これ！",
                    DialogInterface.OnClickListener { _, _ ->
                        binding.registButton.isVisible = true
                        binding.registEditText.isVisible = true
                        presenter.registBook(book)
                    }
                )
                setNegativeButton(
                    "ちがう！",
                    DialogInterface.OnClickListener { _, _ ->
                        binding.registButton.isVisible = true
                        binding.registEditText.isVisible = true
                    })
            }
            builder.show()
        }
    }
}

