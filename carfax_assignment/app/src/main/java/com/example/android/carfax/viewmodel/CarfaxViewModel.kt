package com.example.android.carfax.viewmodel

import androidx.lifecycle.ViewModel
import com.example.android.carfax.network.CarfaxData
import com.example.android.carfax.network.CarfaxAPI
import io.reactivex.Observable

class CarfaxViewModel : ViewModel() {
    fun getCARFAXData() : Observable<CarfaxData>{
        return CarfaxAPI.retrofitService.getData()
    }
}