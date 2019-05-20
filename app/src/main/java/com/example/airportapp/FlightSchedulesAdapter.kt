package com.example.airportapp

import android.content.Context
import android.support.annotation.VisibleForTesting
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.airportapp.api.AirportFlightScheduleModel

class FlightSchedulesAdapter (private var flightsList: ArrayList<AirportFlightScheduleModel>) : RecyclerView.Adapter<FlightSchedulesAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): MyViewHolder {
        val rootView = getLayoutInflater(viewGroup.context).inflate(R.layout.flight_row, viewGroup, false)
        return MyViewHolder(rootView)
    }

    override fun getItemCount(): Int {
        return flightsList.size
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
         val airlineTextView = viewHolder.airlineTextView
         val statusTextView = viewHolder.statusTextView
         val departureTimeTextView = viewHolder.departureTimeTextView
         val flightNumberTextView = viewHolder.flightNumberTextView
         val destinationTextView = viewHolder.destinationTextView
        val statusImageView = viewHolder.statusImageView


        airlineTextView.text = flightsList[position].airline.name
        statusTextView.text = flightsList[position].status
//        departureTimeTextView.text = flightsList[position].departure.scheduledTime!!.substring(14,19)
        flightNumberTextView.text = flightsList[position].flight.number
        destinationTextView.text = flightsList[position].arrival.icaoCode

        if (flightsList[position].status == "active" || flightsList[position].status == "scheduled") {
            statusImageView.setImageResource(R.drawable.green_dot_x1)
        } else {
            statusImageView.setImageResource(R.drawable.red_dot_x1)
        }
    }

    @VisibleForTesting
    internal fun getLayoutInflater(context: Context): LayoutInflater {
        return LayoutInflater.from(context)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var airlineTextView: TextView = itemView.findViewById(R.id.airline)
        internal var statusTextView: TextView = itemView.findViewById(R.id.plane_status)
        internal var departureTimeTextView: TextView = itemView.findViewById(R.id.departure_time_text)
        internal var flightNumberTextView: TextView = itemView.findViewById(R.id.flight_number_text)
        internal var destinationTextView: TextView = itemView.findViewById(R.id.destination_text)
        internal var statusImageView: ImageView = itemView.findViewById(R.id.img_status)
    }
}