package com.mtc_hack.map.data

data class RoomParams(
    val id: Int,
    val lock: String,
    val right_bottom_x: Int,
    val right_bottom_y: Int,
    val left_top_x: Int,
    val left_top_y: Int,
)
