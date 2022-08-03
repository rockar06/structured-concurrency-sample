package com.example.structuredconcurrencysample.view

import androidx.lifecycle.*
import com.example.structuredconcurrencysample.data.Section
import com.example.structuredconcurrencysample.data.SectionRepository
import com.example.structuredconcurrencysample.data.SectionType
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

class MainViewModel(
    private val repository: SectionRepository
) : ViewModel() {

    private val innerTopSection = MutableLiveData<Section>()
    val topSection: LiveData<Section> = innerTopSection
    private val innerMiddleSection = MutableLiveData<Section>()
    val middleSection: LiveData<Section> = innerMiddleSection
    private val innerBottomSection = MutableLiveData<Section>()
    val bottomSection: LiveData<Section> = innerBottomSection

    fun requestData() {
        viewModelScope.launch {
            val time = measureTimeMillis {
                requestBackgroundTopData()
                requestBackgroundMiddleData()
                requestBackgroundBottomData()
            }
            println("Completed in $time ms")
        }
    }

    fun requestTopData() {
        viewModelScope.launch {
            requestBackgroundTopData()
        }
    }

    private suspend fun requestBackgroundTopData() {
        try {
            innerTopSection.value = repository.getTopPost()
        } catch (e: Exception) {
            innerTopSection.value = getErrorSection(SectionType.TOP)
        }
    }

    fun requestMiddleData() {
        viewModelScope.launch {
            requestBackgroundMiddleData()
        }
    }

    private suspend fun requestBackgroundMiddleData() {
        try {
            innerMiddleSection.value = repository.getMiddlePost()
        } catch (e: Exception) {
            innerMiddleSection.value = getErrorSection(SectionType.MIDDLE)
        }
    }

    fun requestBottomData() {
        viewModelScope.launch {
            requestBackgroundBottomData()
        }
    }

    private suspend fun requestBackgroundBottomData() {
        try {
            innerBottomSection.value = repository.getBottomPost()
        } catch (e: Exception) {
            innerMiddleSection.value = getErrorSection(SectionType.BOTTOM)
        }
    }

    private fun getErrorSection(type: SectionType): Section {
        return Section(type = type, isLoading = false, title = "Sorry! Try again.")
    }
}

class MainViewModelFactory(
    private val repository: SectionRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}