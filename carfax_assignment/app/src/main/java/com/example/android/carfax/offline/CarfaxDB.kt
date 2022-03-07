package com.example.android.carfax.offline

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android.carfax.network.CarListing

@Database(entities = arrayOf(CarListing::class), version = 1)
abstract class CarfaxDB : RoomDatabase() {
    abstract fun userDao(): CarfaxDAO
}
