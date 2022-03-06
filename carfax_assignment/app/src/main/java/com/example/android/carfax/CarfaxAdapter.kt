package com.example.android.carfax

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.carfax.network.CarListings
import com.squareup.picasso.Picasso


class CarfaxAdapter (val listener: OnItemClickListener) :
    RecyclerView.Adapter<CarfaxAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick (listing: CarListings)
    }
    inner class ViewHolder(layout: View) : RecyclerView.ViewHolder(
        layout
    ) {

        var carImage: ImageView
        var carModelLine: TextView
        var carShortDescriptionLine: TextView
        var carLocationLine: TextView

        init {
            carImage = layout.findViewById<View>(R.id.CarImg) as ImageView
            carModelLine = layout.findViewById<View>(R.id.CarModel) as TextView
            carShortDescriptionLine = layout.findViewById<View>(R.id.CarShortDescription) as TextView
            carLocationLine = layout.findViewById<View>(R.id.CarLocation) as TextView
        }
    }

    private var values: List<CarListings> = ArrayList()

    fun setData(data: List<CarListings>) {
        values = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(
            parent.context
        )
        val v: View = inflater.inflate(R.layout.row_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listing = values[position]
        Picasso.get().load(listing.images.firstPhoto.large).into(holder.carImage)
        val carModel = listing.year.toString() + " " +
                listing.make + " " +
                listing.model + " " +
                listing.trim
        holder.carModelLine.text = carModel
        val mainScreenDetails = "$" + "%,d".format(listing.currentPrice) +
                " | " + String.format("%.1f", listing.mileage?.toDouble()?.div(1000)) + "k mi"
        val carLocation = listing.dealer.city + ", " + listing.dealer.state
        holder.carShortDescriptionLine.text = mainScreenDetails
        holder.carLocationLine.text = carLocation
        //For the button, create a second onclicklistener that passes the listing, just the first one
        // Differentiate the click functions by button and view clicks
        holder.itemView.setOnClickListener {
            listener.onItemClick(listing)
        }


        // TODO Add listener logic here
        /*
        Plan for the details intent

        1. Create a listener, a variable for that listener, and then add the click listener logic in where you have setup the text data
        (https://stackoverflow.com/questions/49969278/recyclerview-item-click-listener-the-right-way)
        2. After implementing the logic for listener, create a function in activity that can be passed into the listener logics that takes the listitem as a parameter
        Then log out that item
        3. In the override
        */
    }

    override fun getItemCount(): Int {
        return values.size
    }
}