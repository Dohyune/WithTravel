package com.example.withtravel

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.LocationServices


class LocationSettingActivity:AppCompatActivity() {

    //var my_latitude:String
    //var my_longtitude:String

    @SuppressLint("MissingPermission")
    private fun getLocation(textView: TextView) {
        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this)

        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { success: Location? ->
                success?.let { location ->
                    textView.text =
                        "${location.latitude}, ${location.longitude}"
                    //my_latitude = "${location.latitude}"
                    //my_longtitude = "${location.longitude}"
                }
            }
            .addOnFailureListener { fail ->
                textView.text = fail.localizedMessage
            }

    }


    override fun onStart() {
        super.onStart()
        RequestPermissionsUtil(this).requestLocation() // 위치 권한 요청
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_setting)

        val locationText: TextView = findViewById(R.id.locationText)
        val locationButton: Button = findViewById(R.id.locationButton)

        locationButton.setOnClickListener {
            getLocation(locationText)
        }
    }

/*
    var pos: LatLng? = LatLng.from(37.394265, 127.108332)

    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_setting)

        // MapView 초기화
        mapView = findViewById(R.id.map_view)
        val locationButton: Button = findViewById(R.id.locationButton)
        val position = LatLng.from(37.394660, 127.111182)

        locationButton.setOnClickListener {
           // LatLng.getLatitude()
            CameraUpdateFactory.newCenterPosition(LatLng.from(67.402005, 127.108621));
        }


        // MapView 시작 및 콜백 설정
        mapView.start(object:MapLifeCycleCallback(){
            override fun onMapDestroy() {
                // 지도 API가 정상적으로 종료될 때 호출됨
            }

            override fun onMapError(error: Exception) {
                // 인증 실패 및 지도 사용 중 에러가 발생할 때 호출됨
            }
        }, object : KakaoMapReadyCallback() {
            override fun onMapReady(kakaoMap: KakaoMap) {
                // 인증 후 API가 정상적으로 실행될 때 호출됨
            }
        })


    }

    // MapView 생명주기 관리
    override fun onResume() {
        super.onResume()
        mapView.resume() // MapView의 resume 호출
    }

    override fun onPause() {
        super.onPause()
        mapView.pause() // MapView의 pause 호출
    }

        override fun onDestroy() {
            super.onDestroy()
            mapView.destroy() // MapView의 destroy 호출
        }
    */
}