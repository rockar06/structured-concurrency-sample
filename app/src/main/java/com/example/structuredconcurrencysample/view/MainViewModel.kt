package com.example.structuredconcurrencysample.view

import androidx.lifecycle.*
import com.example.structuredconcurrencysample.data.Section
import com.example.structuredconcurrencysample.data.SectionRepository
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
                innerTopSection.value = repository.getTopPost()
                innerMiddleSection.value = repository.getMiddlePost()
                innerBottomSection.value = repository.getBottomPost()
            }
            println("Completed in $time ms")
        }
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