package com.example.android.carfax

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.android.carfax.network.CarListing
import com.example.android.carfax.network.CarfaxAPI
import com.example.android.carfax.network.CarfaxAPIService
import com.example.android.carfax.offline.CarfaxDB
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

        //val model: CarfaxViewModel by viewModels()
        val carfaxAPI: CarfaxAPIService =  CarfaxAPI.retrofitService
        val appDatabase = Room.databaseBuilder(applicationContext,
            CarfaxDB::class.java, "mvvm-database").build()
        val repo: CarfaxRepository = CarfaxRepository(carfaxAPI, appDatabase.carfaxDAO())
        val model: CarfaxViewModel = CarfaxViewModel(repo)
        recyclerView.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        mAdapter = CarfaxAdapter(this)
        recyclerView.adapter = mAdapter

        val disposable = model.getCARFAXData().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mAdapter.setData(it)
            }, {
                Log.e("Error", "${it.localizedMessage}")
            })

        subscriptions.add(disposable)
    }

    override fun onStop() {
        super.onStop()
        subscriptions.clear()
    }
    override fun onItemClick(listing: CarListing) {
        var intent = Intent(this, DetailsActivity::class.java).apply {
            var extras = Bundle()
            extras.putSerializable("CAR", listing)
            putExtras(extras)
        }
        startActivity(intent)
    }

    override fun onButtonClick(listing: CarListing) {
        var intent = Intent(Intent.ACTION_DIAL).apply {
            setData(Uri.parse("tel:" + listing.dealer.phone))
        }
        startActivity(intent)
    }

}