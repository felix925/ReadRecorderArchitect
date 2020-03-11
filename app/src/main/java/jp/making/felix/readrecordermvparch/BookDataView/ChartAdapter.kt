package jp.making.felix.readrecordermvparch.BookDataView

import android.graphics.Color
import android.graphics.Typeface
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import jp.making.felix.readrecordermvparch.data.BookModel.Page

class ChartAdapter{
    private var mTypeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
    /**
     * 二つの必要な関数をまとめて呼び出してあげる関数
     * @param lineChart setupLineChartに渡してあげるLineChartのインスタンス
     * @param page lineDataWithCountに渡してあげる本のページのログ
     *
     * @see setupLineChart
     * @see lineDataWithCount
     */
    fun setUpChart(lineChart: LineChart, page: Array<Page>, maxPage:Int): LineData {
        setupLineChart(lineChart, maxPage)
        return(lineDataWithCount(page))
    }
    /**
     * LineChartの初期化
     * @param lineChart 初期化するLineChartのインスタンス
     */
    private fun setupLineChart(lineChart: LineChart, maxPage: Int){
        lineChart.apply {
            description.isEnabled = false
            setTouchEnabled(true)
            isDragEnabled = false
            isScaleXEnabled = true
            setPinchZoom(false)
            setDrawGridBackground(false)

            //データラベルの表示
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

            //y軸右側の表示
            axisRight.isEnabled = false

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
            val ll1 = LimitLine(maxPage.toFloat(), "Goal!!!")
            ll1.lineWidth = 4f
            ll1.enableDashedLine(10f, 10f, 0f)
            ll1.labelPosition = LimitLabelPosition.RIGHT_TOP
            ll1.textSize = 10f
        }
    }
    /**
     * 渡されたデータをもとにグラフを作成する関数です。
     * @param page 本のページのログ(本来だとここはグラフにしたいデータが入る)
     * @return この関数によって作成されたグラフのデータが返される。
     */
    private fun lineDataWithCount(page:Array<Page>): LineData {
        val values = mutableListOf<Entry>()
        var j = 0
        for (i in page) {
            val value = i.pageData.toFloat()
            values.add(Entry(j.toFloat(), value))
            j++
        }
        // create a dataset and give it a type
        val yVals = LineDataSet(values, "読書ページ推移").apply {
            axisDependency =  YAxis.AxisDependency.LEFT
            color = Color.BLACK
            highLightColor = Color.RED
            setDrawCircles(true)
            setDrawCircleHole(false)
            setDrawValues(true)
            lineWidth = 2f
        }
        val data = LineData(yVals)
        data.setValueTextColor(Color.BLACK)
        data.setValueTextSize(9f)
        return data
    }
}