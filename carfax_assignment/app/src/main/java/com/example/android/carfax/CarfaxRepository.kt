package com.example.android.carfax

import android.util.Log
import com.example.android.carfax.network.CarfaxAPIService
import com.example.android.carfax.network.CarfaxData
import com.example.android.carfax.offline.CarfaxDAO
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class CarfaxRepository(val carfaxAPi: CarfaxAPIService, val carfaxDAO: CarfaxDAO) {

    fun getListings(): Observable<CarfaxData> {
        return Observable.concatArray(
            getListingsFromDb(),
            getListingsFromApi())
    }


    fun getListingsFromDb(): Observable<CarfaxData> {
        return carfaxDAO.getData()
            .toObservable()
            .doOnNext {
                Log.d("Repository", "Dispatching  users from DB...")
            }
    }

    fun getListingsFromApi(): Observable<CarfaxData> {
        return carfaxAPi.getData()
            .doOnNext {
                storeListingsInDb(it)
            }
    }

    fun storeListingsInDb(data: CarfaxData) {
        Observable.fromCallable { carfaxDAO.insert(data) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
            }
    }

}