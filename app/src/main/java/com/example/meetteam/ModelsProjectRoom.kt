package com.example.meetteam.network

//로그인 요청 및 응답
data class LoginRequest(
    val email : String,
    val password : String
)

data class result(
    val tokenResponse:tokenResponse
)
data class tokenResponse(
    val accessToken: token,
    val refreshToken: token
)

data class token(
    val token: String
)

data class LoginResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: result
)
//회원가입
data class MemberSignupRequestDto(
    val email : String,
    val password : String,
    val phoneNumber : String,
    val loginType:String
)

data class MemberSignupResponseDto(
    val isSuccess : Boolean,
    val code : String,
    val message : String,
    val result: String
)

// 방 생성 요청 및 응답
data class CreateRoomRequest(
    val chatroomName: String,
    val totalMember: Int,
    val wantLeader: Boolean
)

data class CreateRoomResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: String
)

// 코드로 방 입장 요청 및 응답
data class EnterRoomRequest(
    val code: String,
    val wantLeader: Boolean
)

data class EnterRoomResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: String
)

// 방 목록 가져오기 응답
data class RoomListResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: List<Room>
)

data class Room(
    val id: Int,
    val chatroomName: String,
    val totalMember: Int
)

// 특정 방 입장 요청 및 응답
data class EnterSpecificRoomRequest(
    val id: Int
)

data class EnterSpecificRoomResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: SpecificRoomDetails
)

data class SpecificRoomDetails(
    val chatroomName: String,
    val totalMember: Int,
    val leaderName: String?,
    val members: List<String?>
)

// 방 삭제 요청 및 응답
data class DeleteRoomRequest(
    val id: Int
)

data class DeleteRoomResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: Any?
)

//