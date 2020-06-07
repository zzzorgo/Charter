package zzz.zzzorgo.charter.ui.statistic

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import zzz.zzzorgo.charter.CharterApplication
import zzz.zzzorgo.charter.R
import zzz.zzzorgo.charter.utils.getDateStringFromSeconds
import javax.inject.Inject


class StatisticFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<StatisticViewModel> { viewModelFactory }
    lateinit var chart: LineChart

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as CharterApplication).appComponent.inject(this)
        viewModel.updateCurrencyValues()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel.balanceEntries.observe(viewLifecycleOwner, Observer { entries ->
            if (chart.data != null) {
                chart.data.clearValues()
            }

            val dataSet = LineDataSet(entries, "Label")
            dataSet.setDrawCircles(false)
            dataSet.mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            dataSet.setDrawCircleHole(false)
            dataSet.lineWidth = 2F
            dataSet.setDrawValues(false)
            dataSet.setDrawFilled(true)
            val lineData = LineData(dataSet)

            chart.data = lineData
            chart.invalidate()
        })

        return inflater.inflate(R.layout.fragment_statistic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chart = view.findViewById(R.id.chart)
        chart.setTouchEnabled(true)
        chart.setPinchZoom(true)
        chart.description.textColor = Color.WHITE
        chart.setNoDataText("Your chart will be here")

//        chart.xAxis.setDrawAxisLine(false)
        chart.xAxis.labelCount = 12
        chart.xAxis.spaceMin = 1000000F
        chart.xAxis.spaceMax = 1000000F
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.xAxis.enableGridDashedLine(2F, 5F, 0F)
        chart.xAxis.textColor = Color.WHITE
        val formatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase): String {
                return getDateStringFromSeconds(value.toLong(), "yyyy MM")
            }
        }
        chart.xAxis.valueFormatter = formatter

        chart.getAxis(YAxis.AxisDependency.RIGHT).isEnabled = false
        chart.getAxis(YAxis.AxisDependency.LEFT).labelCount = 8
        chart.getAxis(YAxis.AxisDependency.LEFT).enableGridDashedLine(2F, 5F, 0F)


        val ll = LimitLine(2_000_000F, "Blood Pressure High")
//        ll.lineColor = Color.RED
//        ll.lineWidth = 4f
        ll.textColor = Color.WHITE
        ll.textSize = 12f
        chart.getAxis(YAxis.AxisDependency.LEFT).addLimitLine(ll)
        chart.getAxis(YAxis.AxisDependency.LEFT).setDrawZeroLine(true)
        chart.getAxis(YAxis.AxisDependency.LEFT).textColor = Color.WHITE


//        chart.getAxis(YAxis.AxisDependency.LEFT).axisMaximum = 20_000_000F

    }
}
