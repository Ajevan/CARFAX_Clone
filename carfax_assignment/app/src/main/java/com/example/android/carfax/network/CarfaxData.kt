package com.example.android.carfax.network

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

data class CarfaxData (val listings: List<CarListing> ) {

}
@Entity(tableName = "listings")
data class CarListing (@PrimaryKey val id: String,
                       val make: String,
                       val model: String,
                       val exteriorColor: String,
                       val interiorColor: String,
                       val mileage: Int,
                       val drivetype: String,
                       val transmission: String,
                       val currentPrice: Int,
                       val year: Int,
                       @Embedded
                       val dealer: Dealer,
                       @Embedded
                       val images: Image,
                       val bodytype: String,
                       val trim: String,
                       val engine: String,
                       val fuel: String) : Serializable {

}

data class Dealer (val city: String, val state: String, val phone: String) : Serializable

data class Image( @Embedded val firstPhoto: FirstPhoto) : Serializable

data class FirstPhoto (val large: String) : Serializable

