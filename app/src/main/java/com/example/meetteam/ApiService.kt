package com.example.meetteam.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("room/create")  // 방 생성 요청을 처리하는 엔드포인트
    fun createRoom(@Body request: CreateRoomRequest): Call<CreateRoomResponse>

    @POST("room/enterCode")
    fun enterRoom(@Body request: EnterRoomRequest): Call<EnterRoomResponse>

    @GET("room/showRoomList")
    fun getRoomList(): Call<RoomListResponse>

    @POST("room/enter")
    fun enterSpecificRoom(@Body request: EnterSpecificRoomRequest):Call<EnterSpecificRoomResponse>

    @POST("room/exit")
    fun deleteRoom(@Body request: DeleteRoomRequest): Call<DeleteRoomResponse>
}
