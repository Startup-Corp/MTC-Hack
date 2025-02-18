package com.mtc_hack.map

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mtc_hack.map.data.ReportData
import com.mtc_hack.map.databinding.ActivityReportBinding

class ReportActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReportBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var floorAdapter: ReportRecyclerAdapter
    private var data = mutableListOf<ReportData>()
    private var lockText: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener(this::onBackButtonClicked)
        lockText = intent.getStringExtra("Description")
        lateinit var reportData: ReportData
        when (lockText) {
            "lock" -> {
                reportData = ReportData(
                    statusNumber = "22",
                    place = "Луначарского ул," + " дом 240 К2," + " кв 8",
                    description = "Препятствие"
                )
            }
            "human" -> {
                reportData = ReportData(
                    statusNumber = "22",
                    place = "Луначарского ул," + " дом 240 К2," + " кв 8",
                    description = "Человек упал"
                )
            }
        }

        data.add(reportData)

        recyclerView = binding.recyclerView
        floorAdapter = ReportRecyclerAdapter(data)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = floorAdapter
    }

    private fun onBackButtonClicked(view: View?) {
        finish()
    }
}