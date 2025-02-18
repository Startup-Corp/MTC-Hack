package com.mtc_hack.map.floors

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mtc_hack.map.DescriptionActivity
import com.mtc_hack.map.MapActivity
import com.mtc_hack.map.databinding.FloorItemBinding
import com.mtc_hack.map.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FloorRecycleAdapter(private val floors: List<Int>, private val context: Context) :
    RecyclerView.Adapter<FloorRecycleAdapter.FloorsViewHolder>() {

    class FloorsViewHolder(val binding: FloorItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FloorsViewHolder {
        val binding = FloorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FloorsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FloorsViewHolder, position: Int) {
        holder.binding.floorTextView.text = "Этаж ${floors[position]}"
        if (floors[position] != 3) {
//            if (floors[position] == 1)
//                holder.binding.floorTextView
//                    .setBackgroundResource(com.mtc_hack.design_system.R.color.green)
//            else if (floors[position] == 2)
//                holder.binding.floorTextView
//                    .setBackgroundResource(com.mtc_hack.design_system.R.color.red)

            holder.binding.floorTextView.setOnClickListener {
                val context: Context = holder.itemView.context
                val intent = Intent(context, MapActivity::class.java)
                intent.putExtra("FLOOR_NUMBER", floors[position])
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = floors.size
}
