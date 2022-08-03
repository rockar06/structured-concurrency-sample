package com.example.structuredconcurrencysample.view

import android.util.Log
import androidx.lifecycle.*
import com.example.structuredconcurrencysample.data.Section
import com.example.structuredconcurrencysample.data.SectionRepository
import com.example.structuredconcurrencysample.data.SectionType
import kotlinx.coroutines.*
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

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            when (throwable) {
                is RuntimeException -> {
                    println("Share the error in the view")
                    //getErrorSection
                }
                else -> Log.e("MainViewModel", "Unknown error!")
            }
        }

    fun requestData() {
        viewModelScope.launch(coroutineExceptionHandler) {
            val time = measureTimeMillis {
                val topSection = requestTopDataAsync()
                val middleSection = requestMiddleDataAsync()
                val bottomSection = requestBottomDataAsync()
                awaitAll(topSection, middleSection, bottomSection)
            }
            println("Completed in $time ms")
        }
    }

    fun requestTopData() {
        viewModelScope.launch {
            requestTopDataAsync().await()
        }
    }

    private fun requestTopDataAsync(): Deferred<Unit> =
        viewModelScope.async(coroutineExceptionHandler) {
            repository.getTopPost().apply {
                innerTopSection.value = this
            }
        }

    fun requestMiddleData() {
        viewModelScope.launch {
            requestMiddleDataAsync().await()
        }
    }

    private fun requestMiddleDataAsync(): Deferred<Unit> =
        viewModelScope.async(coroutineExceptionHandler) {
            repository.getMiddlePost().apply {
                innerMiddleSection.value = this
            }
        }

    fun requestBottomData() {
        viewModelScope.launch {
            requestBottomDataAsync().await()
        }
    }

    private fun requestBottomDataAsync(): Deferred<Unit> =
        viewModelScope.async(coroutineExceptionHandler) {
            repository.getBottomPost().apply {
                innerBottomSection.value = this
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