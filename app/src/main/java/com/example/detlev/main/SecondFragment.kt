package com.example.detlev.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.detlev.main.databinding.FragmentSecondBinding
import com.example.detlev.main.model.MainViewModel
import com.example.detlev.main.network.ErrorCodes
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

    private val viewModel: MainViewModel by activityViewModels()

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

            data = LineData(viewModel.pulsDataSet)
        }


        viewModel.fitnessData.observe(viewLifecycleOwner) {
            Log.i(">>>>>", "new Data")
            with (binding.lineChart) {
                data.notifyDataChanged()
                notifyDataSetChanged()
                invalidate()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}