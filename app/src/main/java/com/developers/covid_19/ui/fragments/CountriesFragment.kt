package com.developers.covid_19.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.developers.covid_19.databinding.CountriesFragmentBinding
import com.developers.covid_19.entities.AllData
import com.developers.covid_19.helpers.EventObserver
import com.developers.covid_19.ui.viewmodels.CountriesViewModel
import com.developers.covid_19.utils.TransparentProgressDialog
import com.developers.covid_19.utils.snackbar
import dagger.hilt.android.AndroidEntryPoint
import com.anychart.AnyChart

import com.anychart.enums.TooltipPositionMode
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.enums.Anchor
import com.anychart.enums.HoverMode
import com.anychart.enums.Position
import com.developers.covid_19.R
import com.developers.covid_19.ui.adapters.CountryAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class CountriesFragment : Fragment() {

    private var _binding: CountriesFragmentBinding? = null
    private val binding get() = _binding!!
    private val countriesViewModel: CountriesViewModel by viewModels()

    @Inject
    lateinit var countryAdapter: CountryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CountriesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        countriesViewModel.getAllDataWorld()
        countriesViewModel.getCountries()
        subscribeToObservers()
        setupRecyclerViewCountry()

    }


    private fun subscribeToObservers() {
        lifecycleScope.launchWhenStarted {
            countriesViewModel.allWorldStatus.collect(EventObserver(
                onLoading = {
                },
                onError = {
                    snackbar(it)
                    Log.i("GAMALRAGAB", "subscribeToObservers: ${it}")

                },
                onSuccess = {
                    Log.i("GAMALRAGAB", "subscribeToObservers: ${it}")
                    addDataToView(it)

                }
            ))
        }
            lifecycleScope.launchWhenStarted {

                countriesViewModel.countriesStatus.collect(EventObserver(
                    onLoading = {
                        TransparentProgressDialog.show(requireContext())
                    },
                    onError = {
                        snackbar(it)
                        TransparentProgressDialog.hideProgress()
                        Log.i("GAMALRAGAB", "countriesStatus: ${it}")

                    },
                    onSuccess = {
                        Log.i("GAMALRAGAB", "countriesStatus: ${it[100]}")
                        Log.i("GAMALRAGAB", "countriesStatus: ${it.size}")
                        TransparentProgressDialog.hideProgress()

                        countryAdapter.countries = it

                    }
                ))

            }


    }

    private fun addDataToView(item: AllData) {
        binding.lineChart.setProgressBar(binding.spinkitLoadingChart)
        val cartesian = AnyChart.column()

        val data: ArrayList<DataEntry> = ArrayList()
        data.add(ValueDataEntry("Active", item.active))
        data.add(ValueDataEntry("Confirmed", item.cases))
        data.add(ValueDataEntry("Deaths", item.deaths))
        data.add(ValueDataEntry("Recovered", item.recovered))


        val column = cartesian.column(data)


        column.tooltip()
            .titleFormat("{%X}")
            .position(Position.CENTER_BOTTOM)
            .anchor(Anchor.CENTER_BOTTOM)
            .offsetX(0.0)
            .offsetY(5.0)
            .format("\${%Value}{groupsSeparator: }")
        column.fill(
            "function() {" +
                    "if (this.value ==${item.active}) return '#FF4CAF50 0.9';\n" +
                    "else if (this.value==${item.cases}) return '#38ACDD 0.9';" +
                    "else if (this.value==${item.deaths}) return '#f55c47 0.9';" +
                    "else if (this.value==${item.recovered}) return '#FFB701 0.9';" +
                    "else return '#FF4CAF50 0.9';}"
        );
        cartesian.animation(true)
        cartesian.title(" Covid-19 info  by Revenue")

        cartesian.yScale().minimum(0.0)

        cartesian.yAxis(0).labels().format("\${%Value}{groupsSeparator: }")

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)
        cartesian.interactivity().hoverMode(HoverMode.BY_X)

        cartesian.xAxis(0).title("Cases")
        cartesian.yAxis(0).title("Revenue")



        binding.lineChart.setChart(cartesian)

    }

    private fun setupRecyclerViewCountry() = binding.recylerCountry.apply {
        itemAnimator = null
        isNestedScrollingEnabled = false
        layoutManager = LinearLayoutManager(requireContext())
        adapter = countryAdapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}