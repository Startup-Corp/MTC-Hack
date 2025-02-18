package com.mtc_hack.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mtc_hack.map.data.ReportData
import com.mtc_hack.map.databinding.ReportItemBinding

class ReportRecyclerAdapter(private val floors: List<ReportData>) :
    RecyclerView.Adapter<ReportRecyclerAdapter.ReportViewHolder>() {

    class ReportViewHolder(val binding: ReportItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val binding = ReportItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val data = floors[position]
        holder.binding.numberStatusTextView.text = data.statusNumber
        holder.binding.placeTextView.text = data.place
        holder.binding.descriptionTextView.text = data.description
    }

    override fun getItemCount() = floors.size
}
