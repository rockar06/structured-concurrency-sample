package com.example.structuredconcurrencysample.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.structuredconcurrencysample.data.Section
import com.example.structuredconcurrencysample.data.SectionType
import com.example.structuredconcurrencysample.databinding.SectionItemBinding

typealias OnClickRetry = (sectionType: SectionType) -> Unit

class MainAdapter(private val onClickRetry: OnClickRetry) :
    ListAdapter<Section, MainAdapter.MainViewHolder>(SectionDiffUtils) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = SectionItemBinding.inflate(layoutInflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MainViewHolder(private val binding: SectionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(section: Section) {
            with(binding) {
                if (section.isLoading) {
                    progressBar.isVisible = true
                    successGroup.isVisible = false
                } else {
                    progressBar.isVisible = false
                    successGroup.isVisible = true
                    tvSectionTitle.text = section.title
                    btnRetrySection.setOnClickListener {
                        onClickRetry(section.type)
                    }
                }
            }
        }
    }
}

private object SectionDiffUtils : DiffUtil.ItemCallback<Section>() {
    override fun areItemsTheSame(oldItem: Section, newItem: Section): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Section, newItem: Section): Boolean {
        return oldItem == newItem
    }
}
