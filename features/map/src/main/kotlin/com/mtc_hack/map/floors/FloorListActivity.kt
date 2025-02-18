package com.mtc_hack.map.floors


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mtc_hack.map.data.RetrofitClient
import com.mtc_hack.map.databinding.ActivityFloorListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FloorListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFloorListBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var floorAdapter: FloorRecycleAdapter
    private var floors = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFloorListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerView
        floorAdapter = FloorRecycleAdapter(floors, baseContext)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = floorAdapter

        val fetchedFloors = mutableListOf(1, 2, 3)
        floors.clear()
        floors.addAll(fetchedFloors)
        floorAdapter.notifyDataSetChanged()
    }
}
