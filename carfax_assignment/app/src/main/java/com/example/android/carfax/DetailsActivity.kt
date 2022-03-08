package com.example.android.carfax

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.android.carfax.network.CarListing
import com.squareup.picasso.Picasso


class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        var listingValues = this.intent.getExtras()
        val listing: CarListing = listingValues?.getSerializable("CAR") as CarListing
        Picasso.get().load(listing.images.firstPhoto.large).resize(412,200)
            .centerCrop().into(findViewById<View>(R.id.carImg) as ImageView)
        val carModelTextView = findViewById<TextView>(R.id.carModel).apply {
            text = listing.year.toString() + " " + listing.make + " " + listing.model + " " + listing.trim
        }
        val carShortDescriptionTextView = findViewById<TextView>(R.id.carShortDescription).apply {
            text = "$" + "%,d".format(listing.currentPrice?.toInt()) + " | " + String.format("%.1f", listing.mileage?.toDouble()?.div(1000)) + "k mi"
        }
        val locationValueTextView = findViewById<TextView>(R.id.locationValue).apply {
            text = listing.dealer.city + ", " + listing.dealer.state
        }
        val carExteriorColorTextView = findViewById<TextView>(R.id.exteriorColorValue).apply {
            text = listing.exteriorColor
        }
        val carInteriorColorTextView = findViewById<TextView>(R.id.interiorColorValue).apply {
            text = listing.interiorColor
        }
        val carDriveTypeTextView = findViewById<TextView>(R.id.driveTypeValue).apply {
            text = listing.drivetype
        }
        val carTransmissionTextView = findViewById<TextView>(R.id.transmissionValue).apply {
            text = listing.transmission
        }
        val carEngineTextView = findViewById<TextView>(R.id.engineValue).apply {
            text = listing.engine
        }
        val carBodyStyleTextView = findViewById<TextView>(R.id.bodyStyleValue).apply {
            text = listing.bodytype
        }

        val carFuelTextView = findViewById<TextView>(R.id.fuelValue).apply {
            text = listing.fuel
        }

        var callDealerButtonDetails: Button = findViewById(R.id.button2)
        callDealerButtonDetails.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                var intent = Intent(Intent.ACTION_DIAL).apply {
                    setData(Uri.parse("tel:" + listing.dealer.phone))
                }
                startActivity(intent)
            }
        })
    }
}