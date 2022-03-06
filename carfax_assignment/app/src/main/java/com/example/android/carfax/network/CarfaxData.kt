package com.example.android.carfax.network

data class CarfaxData (val listings: List<CarListings> ) {

}

data class CarListings (val make: String,
                        val model: String,
                        val exteriorColor: String,
                        val interiorColor: String,
                        val mileage: Int,
                        val drivetype: String,
                        val transmission: String,
                        val currentPrice: Int,
                        val year: Int,
                        val dealer: Dealer,
                        val images: Image,
                        val bodytype: String,
                        val trim: String,
                        val engine: String,
                        val fuel: String) {

}

data class Dealer (val city: String, val state: String, val phone: String)

data class Image( val firstPhoto: FirstPhoto)

data class FirstPhoto (val large: String)

