package com.example.detlev.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.detlev.main.databinding.FragmentFirstBinding
import com.example.detlev.main.model.MainViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fitnessData.observe(viewLifecycleOwner) {
                fitnessData -> binding.textviewFirst.text = "${fitnessData.puls}"
        }

        binding.buttonFirst.setOnClickListener {
            viewModel.getFitnessData()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}