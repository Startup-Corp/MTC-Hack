package com.mtc_hack.map.data

data class FloorData(
    val width: Float,
    val height: Float,
    val rooms: List<RoomParams>,
    val scheme: String
)