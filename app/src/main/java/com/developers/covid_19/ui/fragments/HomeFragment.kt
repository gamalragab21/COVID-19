package com.developers.covid_19.ui.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.developers.covid_19.R
import com.developers.covid_19.databinding.HomeFragmentBinding
import com.developers.covid_19.entities.CovidModelItem
import com.developers.covid_19.entities.CovidParent
import com.developers.covid_19.helpers.EventObserver
import com.developers.covid_19.ui.viewmodels.HomeViewModel
import com.developers.covid_19.utils.snackbar
import com.example.architecturalskeleton.utils.Constants.getTimeAgo
import dagger.hilt.android.AndroidEntryPoint
import org.eazegraph.lib.models.PieModel



@AndroidEntryPoint
class HomeFragment:Fragment() {

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel:HomeViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
          homeViewModel.getCurrentCountrySelect(binding.countryCodeHolder.selectedCountryEnglishName)
        binding.countryCodeHolder.setOnCountryChangeListener {
            homeViewModel.getCurrentCountrySelect(binding.countryCodeHolder.selectedCountryEnglishName)
        }


        subscribeToObservers()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun subscribeToObservers() {
        lifecycleScope.launchWhenStarted {
            homeViewModel.currentCountrySelectedStatus.collect(EventObserver(
                onLoading = {
                binding.progress.isVisible=true
                },
                onError = {
            snackbar(it)
                    binding.progress.isVisible=false

                },
                onSuccess = {
                    binding.progress.isVisible=false

                    addDataToView(it)

                }
            ))


        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    private fun addDataToView(item: CovidModelItem) {
        binding.totalConfirmed.text="${item.cases}"
        binding.totalDeath.text="${item.todayDeaths}"
        binding.totalRecovered.text="${item.todayRecovered}"
        binding.newCases.text="${item.todayCases}"
        binding.lastUpdate.text="Last update : ${getTimeAgo(item.updated,requireContext())}"

        updateGraph(item.active,item.cases,item.recovered,item.deaths)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun updateGraph(active: Int, cases: Int, recovered: Int, deaths: Int) {
        binding.piechart.addPieSlice(PieModel("Confirm", cases.toFloat(),requireContext().getColor(R.color.yellow)))
        binding.piechart.addPieSlice(PieModel("Active", active.toFloat(), requireContext().getColor(R.color.green)))
        binding.piechart.addPieSlice(PieModel("Recover", recovered.toFloat(), requireContext().getColor(R.color.blue)))
        binding.piechart.addPieSlice(PieModel("Deaths", deaths.toFloat(), requireContext().getColor(R.color.red)))
        binding.piechart.startAnimation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}