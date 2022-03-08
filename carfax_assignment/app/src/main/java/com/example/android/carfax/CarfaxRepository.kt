package com.example.android.carfax

import com.example.android.carfax.network.CarListing
import com.example.android.carfax.network.CarfaxAPIService
import com.example.android.carfax.offline.CarfaxDAO
import io.reactivex.Observable

class CarfaxRepository(val carfaxAPI: CarfaxAPIService, val carfaxDAO: CarfaxDAO) {

    fun getListings(): Observable<List<CarListing>> {
        return Observable.concatArray(
            getListingsFromApi(),
            getListingsFromDb())
    }


    fun getListingsFromDb(): Observable<List<CarListing>> {
        return carfaxDAO.getData().toObservable()
    }

    //This function grabs listings from API
    fun getListingsFromApi(): Observable<List<CarListing>> {
        return carfaxAPI.getData()
            .map { a -> a.listings }
            .doOnNext {x ->
                storeListingsInDb(x)
            }.onErrorReturn {
                listOf()
            }
    }

    fun storeListingsInDb(data: List<CarListing>) {
        val insertDataIntoDB = Observable.fromCallable { carfaxDAO.insertAll(data) }
    }

}