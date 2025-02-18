package com.mtc_hack.map.data

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("scan_floor")
    suspend fun getFloorData(
        @Query("floor") floor: Int,
    ): FloorData

    @GET("room_data")
    suspend fun getRoomData(
        @Query("floor") floor: Int,
        @Query("room") room: Int,
    ): CurrentRoomData
}