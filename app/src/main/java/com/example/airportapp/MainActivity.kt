package com.example.airportapp

import android.Manifest
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.airportapp.api.AirportApiClient
import com.example.airportapp.api.NearByAirportModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InputStream

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private var currentLocation: Location? = null
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    val request_code = 101
    private var googleMap: GoogleMap? = null
    private var progressBar: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = ProgressDialog(this)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        progressBar!!.setTitle("Please Wait")
        progressBar!!.setMessage("Loading nearest airports")

        progressBar!!.window!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFD4D9D0")))

        progressBar!!.isIndeterminate = false
        progressBar!!.setCancelable(false)

    }

    override fun onResume() {
        super.onResume()
        progressBar!!.show()
        getLastLocation()
    }

    private fun getLastLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), request_code)
            return
        }

        val task = fusedLocationProviderClient!!.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = location
                val supportMapFragment = supportFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment?
                supportMapFragment!!.getMapAsync(this@MainActivity)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            findNearByAirports()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            request_code -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLastLocation()
                }

                return
            }
        }
    }

    fun findNearByAirports() {
        val jsonString = readJSONFromAsset()

        val collectionType = object : TypeToken<List<NearByAirportModel>>() {}.type
        val nearByAirportList = Gson().fromJson(jsonString, collectionType) as List<NearByAirportModel>

        displayNearestAirports(nearByAirportList)
    }

    private fun displayNearestAirports(nearByAirportList: List<NearByAirportModel>) {
        val markerOption = MarkerOptions()
        for (airport in nearByAirportList) {
            val place = airport.nameAirport
            val lat = java.lang.Double.parseDouble(airport.latitudeAirport)
            val lng = java.lang.Double.parseDouble(airport.longitudeAirport)

            val latLng = LatLng(lat, lng)

            markerOption.position(latLng).title(place)
            markerOption.icon(bitmapDescriptorFromVector(this, R.drawable.ic_pin))

            googleMap!!.animateCamera(CameraUpdateFactory.newLatLng(latLng))
            googleMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 7f))
            googleMap!!.addMarker(markerOption)

            progressBar!!.hide()
        }

        googleMap!!.setOnInfoWindowClickListener { marker -> showDetails(marker.title) }

    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
        val bitmap =
            Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)

        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private fun showDetails(airportName: String) {
        val intent = Intent(this, AirportSchedulesActivity::class.java)
        intent.putExtra("airport_name", airportName)
        startActivity(intent)
    }

    private fun readJSONFromAsset(): String? {
        var json: String? = null

        val inputStream: InputStream = assets.open("nearby_airport.json")
        json = inputStream.bufferedReader().use { it.readText() }

        return json
    }


    private fun getNearestAirports() {

        val apiService = AirportApiClient.getService()

        apiService.getNearByAirports(
            currentLocation!!.latitude.toString(),
            currentLocation!!.latitude.toString(),
            "100"
        )
            .enqueue(object : Callback<NearByAirportModel> {
                override fun onResponse(call: Call<NearByAirportModel>, model: Response<NearByAirportModel>) {
                    if (model.isSuccessful && model.body() != null) {

                    }
                }

                override fun onFailure(call: Call<NearByAirportModel>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "" + t.message, Toast.LENGTH_LONG).show()

                    Log.e("FAILURE", t.message)
                }
            })
    }
}
