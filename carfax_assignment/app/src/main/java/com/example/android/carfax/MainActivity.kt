package com.example.android.carfax

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.carfax.viewmodel.CarfaxViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity() {
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
        mAdapter = CarfaxAdapter()
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

}