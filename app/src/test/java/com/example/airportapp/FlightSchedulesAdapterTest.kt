package com.example.airportapp

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.airportapp.api.AirportFlightScheduleModel
import com.example.airportapp.models.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks

class FlightSchedulesAdapterTest {

    @Mock
    private lateinit var mockView: View

    @Mock
    private lateinit var mockAirlineTextView: TextView
    @Mock
    private lateinit var mockStatusTextView: TextView
    @Mock
    private lateinit var mockDepartureTimeTextView: TextView
    @Mock
    private lateinit var mockFlightNumberTextView: TextView
    @Mock
    private lateinit var mockDestinationTextView: TextView
    @Mock
    private lateinit var mockStatusImageView: ImageView
    @Mock private lateinit var mockAirportFlightScheduleModel: AirportFlightScheduleModel
    @Mock private lateinit var mockDeparture: Departure
    @Mock private lateinit var mockArrival: Arrival
    @Mock private lateinit var mockAirline: Airline
    @Mock private lateinit var mockCodeShared: CodeShared
    @Mock private lateinit var mockFlight: Flight

    @Mock private lateinit var mockActivity: Activity


    private lateinit var viewHolder: FlightSchedulesAdapter.MyViewHolder

    private lateinit var adapter: FlightSchedulesAdapter

    @Before
    fun setUp() {
        initMocks(this)

        Mockito.doReturn(mockAirlineTextView).`when`(mockView).findViewById<TextView>(R.id.airline)
        Mockito.doReturn(mockStatusTextView).`when`(mockView).findViewById<TextView>(R.id.plane_status)
        Mockito.doReturn(mockDepartureTimeTextView).`when`(mockView).findViewById<TextView>(R.id.departure_time_text)
        Mockito.doReturn(mockFlightNumberTextView).`when`(mockView).findViewById<TextView>(R.id.flight_number_text)
        Mockito.doReturn(mockDestinationTextView).`when`(mockView).findViewById<TextView>(R.id.destination_text)
        Mockito.doReturn(mockStatusImageView).`when`(mockView).findViewById<ImageView>(R.id.img_status)

        val flightScheduleList = ArrayList<AirportFlightScheduleModel>()
        flightScheduleList.add(mockAirportFlightScheduleModel)

        adapter = FlightSchedulesAdapter(flightScheduleList)
        viewHolder = FlightSchedulesAdapter.MyViewHolder(mockView)

        `when`(mockView.context).thenReturn(mockActivity)

    }

    @Test
    fun flightScheduleList_should_contain_all_the_schedules_that_are_passed() {
        val flightScheduleList = arrayListOf(
            AirportFlightScheduleModel(
                "Departure",
                "Active",
                mockDeparture,
                mockArrival,
                mockAirline,
                mockFlight,
                mockCodeShared
            ),
            AirportFlightScheduleModel(
                "Departure",
                "Active",
                mockDeparture,
                mockArrival,
                mockAirline,
                mockFlight,
                mockCodeShared
            )
        )

        adapter = FlightSchedulesAdapter(flightScheduleList)

        Assert.assertEquals(2, adapter.itemCount)
    }

    @Test
    fun onCreateViewHolder_should_inflate_flight_schedule_list_item() {
        val mockViewGroup = mock(ViewGroup::class.java)
        val mockLayoutInflater = mock(LayoutInflater::class.java)
        adapter = spy(adapter)

        Mockito.`when`(mockViewGroup.context).thenReturn(Mockito.mock(Context::class.java))
        Mockito.`when`(
            mockLayoutInflater.inflate(anyInt(),
                any(ViewGroup::class.java),
                anyBoolean()
            )
        ).thenReturn(mockView)

        doReturn(mockLayoutInflater).`when`(adapter).getLayoutInflater(mock(Context::class.java))

        adapter.onCreateViewHolder(mockViewGroup, 0)

        Mockito.verify(mockLayoutInflater).inflate(R.layout.flight_row, mockViewGroup, false)
    }

    @Test
    fun onBindViewHolder_views_should_be_bound_to_airport_name() {
        val viewHolder = FlightSchedulesAdapter.MyViewHolder(mockView)
        val authenticatorList = arrayListOf(
            AirportFlightScheduleModel(
                "Departure",
                "Active",
                mockDeparture,
                mockArrival,
                mockAirline,
                mockFlight,
                mockCodeShared
            )
        )
        adapter = FlightSchedulesAdapter(authenticatorList)

        adapter.onBindViewHolder(viewHolder, 0)

        Mockito.verify(mockAirlineTextView)
    }

    @Test
    fun onBindViewHolder_views_should_be_bound_to_flight_status() {
        val viewHolder = FlightSchedulesAdapter.MyViewHolder(mockView)
        val authenticatorList = arrayListOf(
            AirportFlightScheduleModel(
                "Departure",
                "Active",
                mockDeparture,
                mockArrival,
                mockAirline,
                mockFlight,
                mockCodeShared
            )
        )
        adapter = FlightSchedulesAdapter(authenticatorList)

        adapter.onBindViewHolder(viewHolder, 0)

        Mockito.verify(mockStatusTextView)
    }

    @Test
    fun onBindViewHolder_views_should_be_bound_to_departure() {
        val viewHolder = FlightSchedulesAdapter.MyViewHolder(mockView)
        val authenticatorList = arrayListOf(
            AirportFlightScheduleModel(
                "Departure",
                "Active",
                mockDeparture,
                mockArrival,
                mockAirline,
                mockFlight,
                mockCodeShared
            )
        )

        adapter = FlightSchedulesAdapter(authenticatorList)

        adapter.onBindViewHolder(viewHolder, 0)

        Mockito.verify(mockDepartureTimeTextView)
    }

    @Test
    fun onBindViewHolder_views_should_be_bound_to_flight_number() {
        val viewHolder = FlightSchedulesAdapter.MyViewHolder(mockView)
        val authenticatorList = arrayListOf(
            AirportFlightScheduleModel(
                "Departure",
                "Active",
                mockDeparture,
                mockArrival,
                mockAirline,
                mockFlight,
                mockCodeShared
            )
        )
        adapter = FlightSchedulesAdapter(authenticatorList)

        adapter.onBindViewHolder(viewHolder, 0)



        Mockito.verify(mockFlightNumberTextView)
    }

    @Test
    fun onBindViewHolder_views_should_be_bound_to_destination() {
        val viewHolder = FlightSchedulesAdapter.MyViewHolder(mockView)
        val authenticatorList = arrayListOf(
            AirportFlightScheduleModel(
                "Departure",
                "Active",
                mockDeparture,
                mockArrival,
                mockAirline,
                mockFlight,
                mockCodeShared
            )
        )
        adapter = FlightSchedulesAdapter(authenticatorList)

        adapter.onBindViewHolder(viewHolder, 0)

        Mockito.verify(mockAirlineTextView)
    }

    @Test
    fun onBindViewHolder_views_should_be_bound_to_status_image() {
        val viewHolder = FlightSchedulesAdapter.MyViewHolder(mockView)
        val authenticatorList = arrayListOf(
            AirportFlightScheduleModel(
                "Departure",
                "Active",
                mockDeparture,
                mockArrival,
                mockAirline,
                mockFlight,
                mockCodeShared
            )
        )
        adapter = FlightSchedulesAdapter(authenticatorList)

        adapter.onBindViewHolder(viewHolder, 0)

        Mockito.verify(mockStatusImageView)
    }

}