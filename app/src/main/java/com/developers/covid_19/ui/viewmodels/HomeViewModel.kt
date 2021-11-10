package com.developers.covid_19.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developers.covid_19.entities.CovidModelItem
import com.developers.covid_19.entities.CovidParent
import com.developers.covid_19.helpers.Event
import com.developers.covid_19.qualifiers.MainThread
import com.developers.covid_19.repositiories.DefaultHomeRepository
import com.example.architecturalskeleton.helpers.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject constructor(
    private val repository: DefaultHomeRepository,
    @MainThread
    private val dispatcher: CoroutineDispatcher

): ViewModel() {

    // TODO: 11/8/2021 Simple way to show how to handle repo with view model by using livedata
    private val _currentCountrySelectedStatus= MutableStateFlow<Event<Resource<CovidModelItem>>>(Event(Resource.Loading()))
    val currentCountrySelectedStatus: MutableStateFlow<Event<Resource<CovidModelItem>>> =_currentCountrySelectedStatus

    fun  getCurrentCountrySelect(countrySelect: String) {


        viewModelScope.launch(dispatcher) {
            val result=repository.getDataForSelectedCountry(countrySelect)
            _currentCountrySelectedStatus.emit(Event(result))
        }

    }

}