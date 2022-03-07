package com.example.android.carfax.offline

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Dao
import com.example.android.carfax.network.CarListing
import com.example.android.carfax.network.CarfaxData
import io.reactivex.Single

@Dao
interface CarfaxDAO {
    @Query ("SELECT * FROM listings")
    fun getData(): Single<CarfaxData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(listing: CarfaxData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(listings: List<CarfaxData>)
}