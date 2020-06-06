package zzz.zzzorgo.charter.ui.statistic

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import zzz.zzzorgo.charter.CharterApplication
import zzz.zzzorgo.charter.R
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
            val lineData = LineData(dataSet)
            chart.data = lineData
            chart.invalidate()
        })

        return inflater.inflate(R.layout.fragment_statistic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chart = view.findViewById(R.id.chart)
    }
}
