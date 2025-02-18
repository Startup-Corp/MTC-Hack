package com.mtc_hack.map

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mtc_hack.map.data.FloorData
import com.mtc_hack.map.data.RetrofitClient
import com.mtc_hack.map.data.RoomParams
import com.mtc_hack.map.databinding.ActivityMapBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapBinding
    private var floorNumber: Int = 0
    private var list = mutableListOf<RoomParams>()
    private lateinit var floorData: FloorData
    private lateinit var bitmapParams: Pair<Int, Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener(this::onBackButtonPressed)

        floorNumber = intent.getIntExtra("FLOOR_NUMBER", 0)

        lifecycleScope.launch {
            try {
                val data = fetchFloorData(floorNumber)
                val bitmap = decodeBase64ToBitmap(data.scheme)
                val highlightedBitmap = highlightRooms(data, bitmap)
                floorData = data
                list.addAll(data.rooms)
                binding.mapImageView.setImageBitmap(highlightedBitmap)
                bitmapParams = Pair(binding.mapImageView.height, binding.mapImageView.width)
                setOnImageClickListener()
            } catch (e: Exception) {
                Log.d("MapActivity", e.message.toString())
                showErrorMessage("Failed to load data")
            }
        }
    }

    private suspend fun fetchFloorData(floorNumber: Int): FloorData {
        return withContext(Dispatchers.IO) {
            RetrofitClient.instance.getFloorData(floorNumber)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setOnImageClickListener() {
        binding.mapImageView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val x = event.x
                val y = event.y
                Log.d("MapActivity", "event:$x, $y")

                val clickedRoom = list.find { room ->
                    val realWidth = floorData.width
                    val realHeight = floorData.height
                    val imageWidth = bitmapParams.second
                    val imageHeight = bitmapParams.first
                    val scaleX = imageWidth.toFloat() / realWidth
                    val scaleY = imageHeight.toFloat() / realHeight
                    val left = room.left_top_x * scaleX
                    val top = room.left_top_y * scaleY
                    val right = room.right_bottom_x * scaleX
                    val bottom = room.right_bottom_y * scaleY

                    x in left..right && y in top..bottom
                }
                Log.d("MapActivity", "room: $clickedRoom")

                if (clickedRoom != null) {
                    val intent = Intent(this, DescriptionActivity::class.java)
                    intent.putExtra("ROOM_DATA", clickedRoom.id)
                    intent.putExtra("FLOOR_NUMBER", floorNumber)
                    intent.putExtra("LOCK", clickedRoom.lock)
                    startActivity(intent)
                }

                binding.mapImageView.performClick()
            }
            true
        }
    }


    private fun decodeBase64ToBitmap(base64String: String): Bitmap {
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

    private fun highlightRooms(floorData: FloorData, bitmap: Bitmap): Bitmap {
        val realWidth = floorData.width
        val realHeight = floorData.height
        val imageWidth = bitmap.width
        val imageHeight = bitmap.height
        val scaleX = imageWidth.toFloat() / realWidth
        val scaleY = imageHeight.toFloat() / realHeight
        val mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(mutableBitmap)

        floorData.rooms.forEach { room ->
            val left = room.left_top_x * scaleX
            val top = room.left_top_y * scaleY
            val right = room.right_bottom_x * scaleX
            val bottom = room.right_bottom_y * scaleY

            val fillPaint = Paint().apply {
                color = when (room.lock) {
                    "none" -> Color.GREEN
                    "lock" -> Color.YELLOW
                    "human" -> Color.RED
                    else -> {
                        Color.GRAY
                    }
                }
                alpha = 128
            }

            canvas.drawRect(left, top, right, bottom, fillPaint)

            val borderPaint = Paint().apply {
                color = Color.BLACK
                style = Paint.Style.STROKE
                strokeWidth = 2f
            }

            canvas.drawRect(left, top, right, bottom, borderPaint)
        }

        return mutableBitmap
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun onBackButtonPressed(view: View?) {
        finish()
    }
}
