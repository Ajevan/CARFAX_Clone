package com.example.android.carfax.viewmodel

import androidx.lifecycle.ViewModel
import com.example.android.carfax.CarfaxRepository
import com.example.android.carfax.network.CarListing
import io.reactivex.Observable

class CarfaxViewModel(val repo: CarfaxRepository): ViewModel() {
    fun getCARFAXData() : Observable<List<CarListing>>{
        return repo.getListings()
    }
}