package com.example.airportapp

import android.app.ProgressDialog
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.example.airportapp.api.AirportApiClient
import com.example.airportapp.api.AirportFlightScheduleModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InputStream
import java.util.*

class AirportSchedulesActivity : AppCompatActivity() {
    private val SHARE_PREFERENCES_FILE = "flight_schedule"
    private val SHARED_PREFERENCES_KEY = "flight_schedule_key"

    private var airportFlightFlightSchedule: ArrayList<AirportFlightScheduleModel>? = null
    private var recyclerView: RecyclerView? = null

    private var progressBar: ProgressDialog? = null
    private var sharedPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    internal var gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_airport_schedules)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp)

        sharedPreferences = applicationContext.getSharedPreferences(SHARE_PREFERENCES_FILE, MODE_PRIVATE)

        progressBar = ProgressDialog(this)
        recyclerView = findViewById(R.id.announcements_recycler_view)
        recyclerView!!.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.itemAnimator = DefaultItemAnimator()

        airportFlightFlightSchedule = ArrayList()

        val intent = intent

        if(intent != null && intent.extras != null) {
            val airportName = intent.getStringExtra("airport_name")
            if(airportName.contains("(", true)) {
                val lastIndex = airportName.indexOf("(", 0, true)
                findViewById<TextView>(R.id.airportName).text = airportName.substring(0, lastIndex)
            } else {
                findViewById<TextView>(R.id.airportName).text = airportName
            }
        }

        progressBar!!.setTitle("Please wait")
        progressBar!!.setMessage("Loading Airport Schedule.........")

        progressBar!!.window!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFD4D9D0")))

        progressBar!!.isIndeterminate = false
        progressBar!!.setCancelable(false)

        getTimeTable()
    }

    fun getTimeTable() {
        val jsonString = readJSONFromAsset()

        val collectionType = object : TypeToken<List<AirportFlightScheduleModel>>() {
        }.type
        val lcs = Gson().fromJson(jsonString, collectionType) as List<AirportFlightScheduleModel>

        val adapter = FlightSchedulesAdapter(ArrayList(lcs))
        recyclerView!!.adapter = adapter

    }

    private fun readJSONFromAsset(): String? {
        var json: String? = null

            val  inputStream: InputStream = assets.open("flight_schedule.json")
            json = inputStream.bufferedReader().use{it.readText()}

        return json
    }

    private fun getFlightsSchedule() {
        val apiService = AirportApiClient.getService()

        apiService.getAirportSchedules("JNB", "departure")
            .enqueue(object : Callback<AirportFlightScheduleModel> {
                override fun onResponse(call: Call<AirportFlightScheduleModel>, model: Response<AirportFlightScheduleModel>) {
                    progressBar!!.dismiss()
                    if (model.isSuccessful && model.body() != null) {
                        airportFlightFlightSchedule!!.add(model.body()!!)
                        editor = sharedPreferences!!.edit()
                        editor!!.putString(SHARED_PREFERENCES_KEY, gson.toJson(model.body()))
                        editor!!.apply()
                        Log.e("SUCCESS", model.body().toString())
                    }
                }

                override fun onFailure(call: Call<AirportFlightScheduleModel>, t: Throwable) {
                    progressBar!!.dismiss()
                    Toast.makeText(this@AirportSchedulesActivity, "Sorry, something went wrong, please try again", Toast.LENGTH_LONG).show()

                    Log.e("FAILURE", t.message)
                }
            })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }
}
