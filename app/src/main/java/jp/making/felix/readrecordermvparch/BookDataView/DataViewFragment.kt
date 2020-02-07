package jp.making.felix.readrecordermvparch.BookDataView

import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.charts.LineChart
import com.google.android.material.floatingactionbutton.FloatingActionButton
import jp.making.felix.readrecordermvparch.Base.BaseFragment
import jp.making.felix.readrecordermvparch.R
import jp.making.felix.readrecordermvparch.data.Model.BookRepository
import jp.making.felix.readrecordermvparch.data.Model.LocalBookModel
import jp.making.felix.readrecordermvparch.data.Model.RemoteBookModel
import jp.making.felix.readrecordermvparch.data.Page
import kotlin.reflect.jvm.internal.impl.descriptors.EffectiveVisibility

class DataViewFragment: Fragment(), DataViewContract.View, BaseFragment {
    override lateinit var presenter: DataViewContract.Presenter
    lateinit var position:String
    lateinit var chart:LineChart
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.book_data_fragment,container,false)
        DataViewPresenter(BookRepository(LocalBookModel(),RemoteBookModel()),this)
        // Bundleを取得する
        val bundle = arguments
        // Bundleがセットされていたら値を受け取る
        if (bundle != null) {
            position = bundle.getString("id") ?: "0"
        }
        activity?.findViewById<FloatingActionButton>(R.id.fab)?.setOnClickListener{
            FabAction()
        }
        SetUpChart(position)
        SetUpThoughtList(position)

        return view
    }

    override fun FabAction() {
        findNavController().navigate(R.id.action_data_to_list)
    }

    override fun deleteProgress() {
    }

    override fun showProgress() {
    }

    override fun setUpButtonIcon() {
    }

    private fun SetUpChart(position:String){
        val chartAdapt = ChartAdapter()
        val pageData:Pair<Array<Page>,Int> = presenter.getPageData(position)
        chartAdapt.setUpChart(chart,pageData.first,pageData.second)
    }
    private fun SetUpThoughtList(position: String){
        val thought = presenter.getThoughtData(position)

    }
}