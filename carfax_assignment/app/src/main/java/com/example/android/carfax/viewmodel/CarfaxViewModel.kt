package com.example.android.carfax.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.android.carfax.CarfaxRepository
import com.example.android.carfax.network.CarfaxData
import com.example.android.carfax.network.CarfaxAPI
import io.reactivex.Observable

class CarfaxViewModel(val repo: CarfaxRepository): ViewModel() {

    fun getCARFAXData() : Observable<CarfaxData>{
        return repo.getListings()
            .doOnError {
                Log.d("ViewModel","An error occurred")
            }

    }

}