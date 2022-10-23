package com.example.detlev.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.detlev.main.databinding.FragmentSecondBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var dataSeries: ArrayList<Entry> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Graph Layout
        with (binding.lineChart) {
            description.isEnabled = false
            axisLeft.axisMinimum = 60f
            axisLeft.axisMaximum = 150f
            axisRight.isEnabled = false
            xAxis.labelRotationAngle = 90f
            xAxis.labelCount = 10

            //enable scrolling and scaling
            isDragEnabled = true
            isScaleXEnabled = true
            isScaleYEnabled = true
        }

        // add some testdata
        dataSeries.add(Entry(1.0f, 100.0f))
        dataSeries.add(Entry(2.0f, 120.0f))
        dataSeries.add(Entry(3.0f, 90.0f))
        dataSeries.add(Entry(4.0f, 110.0f))
        dataSeries.add(Entry(5.0f, 85.0f))

        // display graph
        binding.lineChart.data = LineData(LineDataSet(dataSeries, "Testdaten"))
        binding.lineChart.invalidate()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
}