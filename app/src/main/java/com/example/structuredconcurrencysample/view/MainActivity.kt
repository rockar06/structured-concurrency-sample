package com.example.structuredconcurrencysample.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.structuredconcurrencysample.data.Section
import com.example.structuredconcurrencysample.data.SectionRepositoryImpl
import com.example.structuredconcurrencysample.data.SectionType
import com.example.structuredconcurrencysample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val sectionAdapter = MainAdapter {

    }
    private var listOfSections = mutableListOf(
        Section(type = SectionType.TOP),
        Section(type = SectionType.MIDDLE),
        Section(type = SectionType.BOTTOM),
    )

    private val viewModel by viewModels<MainViewModel> {
        MainViewModelFactory(SectionRepositoryImpl())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        setupObservers()
        viewModel.requestData()
    }

    private fun setupObservers() {
        viewModel.topSection.observe(this) {
            listOfSections = listOfSections.toMutableList().apply {
                set(0, it)
            }
            sectionAdapter.submitList(listOfSections)
        }
        viewModel.middleSection.observe(this) {
            listOfSections = listOfSections.toMutableList().apply {
                set(1, it)
            }
            sectionAdapter.submitList(listOfSections)
        }
        viewModel.bottomSection.observe(this) {
            listOfSections = listOfSections.toMutableList().apply {
                set(2, it)
            }
            sectionAdapter.submitList(listOfSections)
        }
    }

    private fun setupRecyclerView() {
        binding.rvItems.adapter = sectionAdapter
        sectionAdapter.submitList(listOfSections)
    }
}