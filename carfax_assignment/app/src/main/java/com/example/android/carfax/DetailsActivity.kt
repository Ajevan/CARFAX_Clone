package com.example.android.carfax

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso


class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        //Log.i("INFO", intent.getExtras()?.getString("CAR_IMG").toString())
        var listingValues = this.intent.getExtras()
        var carImg = listingValues?.getString("CAR_IMG");
        Picasso.get().load(carImg).into(findViewById<View>(R.id.carImg) as ImageView)
        var carYear = listingValues?.getString("CAR_YEAR");
        var carMake = listingValues?.getString("CAR_MAKE");
        var carModel = listingValues?.getString("CAR_MODEL");
        var carTrim = listingValues?.getString("CAR_TRIM");
        val carModelTextView = findViewById<TextView>(R.id.carModel).apply {
            text = carYear + " " + carMake + " " + carModel + " " + carTrim
        }
        var carPrice = listingValues?.getString("CAR_PRICE");
        var carMileage = listingValues?.getString("CAR_MILEAGE");
        val carShortDescriptionTextView = findViewById<TextView>(R.id.carShortDescription).apply {
            text = "$" + "%,d".format(carPrice?.toInt()) + " | " + String.format("%.1f", carMileage?.toDouble()?.div(1000)) + " mi"
        }
        var carLocation = listingValues?.getString("CAR_LOCATION");
        val locationValueTextView = findViewById<TextView>(R.id.locationValue).apply {
            text = carLocation
        }
        var carExteriorColor = listingValues?.getString("CAR_EXTERIOR_COLOR");
        val carExteriorColorTextView = findViewById<TextView>(R.id.exteriorColorValue).apply {
            text = carExteriorColor
        }
        var carInteriorColor = listingValues?.getString("CAR_INTERIOR_COLOR");
        val carInteriorColorTextView = findViewById<TextView>(R.id.interiorColorValue).apply {
            text = carInteriorColor
        }
        var carDriveType = listingValues?.getString("CAR_DRIVETYPE");
        val carDriveTypeTextView = findViewById<TextView>(R.id.driveTypeValue).apply {
            text = carDriveType
        }
        var carTransmission = listingValues?.getString("CAR_TRANSMISSION");
        val carTransmissionTextView = findViewById<TextView>(R.id.transmissionValue).apply {
            text = carTransmission
        }
        var carEngine = listingValues?.getString("CAR_ENGINE");
        val carEngineTextView = findViewById<TextView>(R.id.engineValue).apply {
            text = carEngine
        }
        var carBodyStyle = listingValues?.getString("CAR_BODYTYPE");
        val carBodyStyleTextView = findViewById<TextView>(R.id.bodyStyleValue).apply {
            text = carBodyStyle
        }
        var carFuel = listingValues?.getString("CAR_BODYTYPE");
        val carFuelTextView = findViewById<TextView>(R.id.fuelValue).apply {
            text = carFuel
        }
        var dealerPhoneNumber = listingValues?.getString("DEALER_PHONENUMBER");
    }
}