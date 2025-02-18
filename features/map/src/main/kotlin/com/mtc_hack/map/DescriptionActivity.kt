package com.mtc_hack.map

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mtc_hack.map.data.CurrentRoomData
import com.mtc_hack.map.data.RetrofitClient
import com.mtc_hack.map.databinding.ActivityDescriptionBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DescriptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDescriptionBinding
    private var roomNumber: Int = 0
    private var floorNumber: Int = 0
    private var lockText: String? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        roomNumber = intent.getIntExtra("ROOM_DATA", -5)
        floorNumber = intent.getIntExtra("FLOOR_NUMBER", -5)
        lockText = intent.getStringExtra("LOCK")
        binding.title.text = "Этаж $floorNumber"
        binding.backButton.setOnClickListener(this::onBackButtonClicked)
        when (lockText) {
            "none" -> {
                binding.textView.visibility = View.GONE
                binding.disableButton.visibility = View.GONE
                binding.watchButton.visibility = View.GONE
            }
        }
        lifecycleScope.launch {
            try {
                val data = fetchFloorData()
                binding.description.text = data.reason
                val bitmap = decodeBase64ToBitmap(data.image)
                binding.imageView.setImageBitmap(bitmap)
            } catch (e: Exception) {
                Log.d("DescriptionActivity", e.message.toString())
            }
        }

        binding.watchButton.setOnClickListener {
            val intent = Intent(this, ReportActivity::class.java)
            intent.putExtra("Description", lockText)
            startActivity(intent)
        }
    }

    private suspend fun fetchFloorData(): CurrentRoomData {
        return withContext(Dispatchers.IO) {
            RetrofitClient.instance.getRoomData(floorNumber, roomNumber)
        }
    }

    private fun decodeBase64ToBitmap(base64String: String): Bitmap {
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

    private fun onBackButtonClicked(view: View?) {
        finish()
    }
}