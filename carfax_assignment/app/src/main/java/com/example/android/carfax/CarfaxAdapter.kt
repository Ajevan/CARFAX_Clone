package com.example.android.carfax

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.carfax.network.CarListings
import com.squareup.picasso.Picasso


class CarfaxAdapter (val listener: OnItemClickListener) :
    RecyclerView.Adapter<CarfaxAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick (listing: CarListings)
        fun onButtonClick (listing: CarListings)
    }
    inner class ViewHolder(layout: View) : RecyclerView.ViewHolder(
        layout
    ) {

        var carImage: ImageView
        var carModelLine: TextView
        var carShortDescriptionLine: TextView
        var carLocationLine: TextView
        var callDealerButton: Button

        init {
            carImage = layout.findViewById<View>(R.id.CarImg) as ImageView
            carModelLine = layout.findViewById<View>(R.id.CarModel) as TextView
            carShortDescriptionLine = layout.findViewById<View>(R.id.CarShortDescription) as TextView
            carLocationLine = layout.findViewById<View>(R.id.CarLocation) as TextView
            callDealerButton = layout.findViewById<View>(R.id.button) as Button
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
        Picasso.get().load(listing.images.firstPhoto.large).resize(360,201)
            .centerCrop().into(holder.carImage)
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
        /*
        For the call dealer button, create a second onclicklistener that passes the listing, just
        with the dealer info, then differentiate the click functions by button and view clicks,
        this would be for the call dealer functionality
        */
        holder.itemView.setOnClickListener {
            listener.onItemClick(listing)
        }
        holder.callDealerButton.setOnClickListener{
            listener.onButtonClick(listing)
        }
    }

    override fun getItemCount(): Int {
        return values.size
    }
}