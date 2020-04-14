package jp.making.felix.readrecordermvparch.util

import android.graphics.Color
import android.graphics.Typeface
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import jp.making.felix.readrecordermvparch.data.BookModel.Book
import jp.making.felix.readrecordermvparch.data.BookModel.Page

class ChartAdapter(
    private val lineChart: LineChart,
    private val book: Book
) {
    fun setup() {
        val data = makeLineData(book.pages.toList())
        val dataset = LineDataSet(data, "page")
        val mTypeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)

        dataset.setCircleColor(Color.BLACK)
        dataset.color = Color.BLACK
        lineChart.apply {
            description.text = "読んだページの推移"
            setDrawGridBackground(true)
            setScaleMinima(0f, 0f)
            axisRight.isEnabled = false
            axisLeft.granularity = 1f
            xAxis.addLimitLine(LimitLine(book.maxPage.toFloat()))
            setPinchZoom(false)
            this.data = LineData(dataset)

            legend.apply {
                form = Legend.LegendForm.LINE
                typeface = mTypeface
                textSize = 11f
                textColor = Color.BLACK
                verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
                orientation = Legend.LegendOrientation.HORIZONTAL
                setDrawInside(false)
            }
            //X軸表示
            xAxis.apply {
                typeface = mTypeface
                setDrawLabels(false)
                setDrawGridLines(true)
            }

            //y軸左側の表示
            axisLeft.apply {
                typeface = mTypeface
                textColor = Color.BLACK
                setDrawGridLines(true)
            }
        }
    }

    private fun makeLineData(pages: List<Page>): List<Entry> {
        val entries = mutableListOf<Entry>()
        var x = 0
//        for(i in pages){
//            entries.add(Entry(if(x==0) 0f else entries[x].x+1f,i.pageData.toFloat()))
//            x++
//        }
        entries.add(Entry(0f, 0f))
        entries.add(Entry(1f, 1f))
        return entries
    }
}