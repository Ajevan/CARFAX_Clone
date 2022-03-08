package com.example.android.carfax

import android.util.Log
import com.example.android.carfax.network.CarListing
import com.example.android.carfax.network.CarfaxAPIService
import com.example.android.carfax.offline.CarfaxDAO
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class CarfaxRepository(val carfaxAPI: CarfaxAPIService, val carfaxDAO: CarfaxDAO) {

    fun getListings(): Observable<List<CarListing>> {
        return Observable.concatArray(
            getListingsFromApi(),
            getListingsFromDb())
    }


    fun getListingsFromDb(): Observable<List<CarListing>> {
        return carfaxDAO.getData().toObservable()
    }

    fun getListingsFromApi(): Observable<List<CarListing>> {
        return carfaxAPI.getData()
            .map { data -> data.listings }
            .doOnNext {listings ->
                storeListingsInDb(listings)
            }.onErrorReturn {
                listOf()
            }
    }

    fun storeListingsInDb(data: List<CarListing>) {
        val insertDataIntoDB = Observable.fromCallable { carfaxDAO.insertAll(data) }.subscribeOn(
            Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe()
    }

}