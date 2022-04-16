package com.example.detlev.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.detlev.main.databinding.FragmentFirstBinding
import com.example.detlev.main.model.MainViewModel
import com.example.detlev.main.network.ErrorCodes
import kotlinx.coroutines.Job

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private val TAG = "FirstFragment"

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var dataLoadJob: Job
    private var dataIsLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with (binding.speedView) {
            unit = ""
            minSpeed = 30F
            maxSpeed = 170F
            withTremble = false
            speedTo(30F,0L)
        }

        viewModel.fitnessData.observe(viewLifecycleOwner) {
                fitnessData -> when (fitnessData.errorcode) {
                    ErrorCodes.NO_ERROR -> binding.speedView.speedTo(fitnessData.puls.toFloat())
                    //ErrorCodes.INTERNET_ERROR -> Log.i(TAG, "Internet Error")
                    //ErrorCodes.JSON_ERROR -> Log.i(TAG, "JSON Error")
            }
        }

        binding.buttonFirst.setOnClickListener {
            when (dataIsLoading) {
                false -> {
                    dataLoadJob = viewModel.startRepeatingDataLoadJob(1000)
                    binding.buttonFirst.text = getString(R.string.stop_load_data)
                }
                true -> {
                    dataLoadJob.cancel()
                    binding.buttonFirst.text = getString(R.string.start_load_data)
                }
            }
            dataIsLoading = !dataIsLoading
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}