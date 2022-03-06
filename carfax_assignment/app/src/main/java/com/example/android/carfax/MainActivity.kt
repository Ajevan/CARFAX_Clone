package com.example.android.carfax

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.carfax.network.CarListings
import com.example.android.carfax.viewmodel.CarfaxViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity(), CarfaxAdapter.OnItemClickListener {
    private val subscriptions = CompositeDisposable()
    private lateinit var recyclerView: RecyclerView
    private lateinit var mAdapter: CarfaxAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById<View>(R.id.my_recycler_view) as RecyclerView

        val model: CarfaxViewModel by viewModels()
        recyclerView.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        mAdapter = CarfaxAdapter(this)
        recyclerView.adapter = mAdapter

        val disposable = model.getCARFAXData().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mAdapter.setData(it.listings)
            }, {
                Log.e("Error", "Failed to find API")
            })

        subscriptions.add(disposable)
    }
    // This helps with memory leaks, so that subscriptions does not run on forever
    override fun onStop() {
        super.onStop()
        subscriptions.clear()
    }
    /*
    Next steps
    1. use this override to pass the listing into an intent function and call it so
    that goes to the next screen
    2. Display items like in the instructions and go from there
    For back to the main screen, just use the back button
    3. If in case when clicking on call dealer it does not work as expected,
    then group the card elements together and do an onclicklistener on each function
    */
    override fun onItemClick(listing: CarListings) {
        var intent = Intent(this, DetailsActivity::class.java).apply {
            var extras = Bundle()
            extras.putString("CAR_IMG", listing.images.firstPhoto.large)
            extras.putString("CAR_YEAR", listing.year.toString())
            extras.putString("CAR_MAKE", listing.make)
            extras.putString("CAR_MODEL", listing.model)
            extras.putString("CAR_TRIM", listing.trim)
            extras.putString("CAR_PRICE", listing.currentPrice.toString())
            extras.putString("CAR_MILEAGE", listing.mileage.toString())
            extras.putString("CAR_LOCATION", listing.dealer.city + ", " + listing.dealer.state)
            extras.putString("CAR_INTERIOR_COLOR", listing.interiorColor)
            extras.putString("CAR_EXTERIOR_COLOR", listing.exteriorColor)
            extras.putString("CAR_DRIVETYPE", listing.drivetype)
            extras.putString("CAR_TRANSMISSION", listing.transmission)
            extras.putString("CAR_ENGINE", listing.engine)
            extras.putString("CAR_BODYTYPE", listing.bodytype)
            extras.putString("CAR_FUEL", listing.fuel)
            extras.putString("DEALER_PHONENUMBER", listing.dealer.phone)

            //serializedlist = ObjectOutputStream.wr
            //extras.putSerializable()
            putExtras(extras)
        }
        startActivity(intent)
    }

}