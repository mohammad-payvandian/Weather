package com.example.weather.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.weather.R
import com.example.weather.databinding.FragmentMainWeatherBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainWeatherFragment : Fragment() {
    private lateinit var binding: FragmentMainWeatherBinding
    private val viewModel: WeatherViewModel by activityViewModels()
    private var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        collectWeatherState()
    }

    private fun setupListeners() {
        binding.weatherButton.setOnClickListener {
            val city = binding.cityNameInput.text.toString().trim()
            if (city.isNotEmpty()) {
                viewModel.getCurrentWeather(city)
            } else {
                Toast.makeText(requireContext(), "Please enter a city name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun collectWeatherState() {
        job?.cancel()
        job = lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.weatherState.collect { state ->
                    when (state) {
                        is WeatherUiState.Empty -> {
                            binding.progressBar.visibility = View.GONE
                        }
                        is WeatherUiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is WeatherUiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
                        }
                        is WeatherUiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                requireContext(),
                                state.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        job?.cancel()
    }
}