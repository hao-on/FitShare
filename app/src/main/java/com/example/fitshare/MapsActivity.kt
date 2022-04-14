package com.example.fitshare

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.core.app.ActivityCompat
import com.example.fitshare.Profile.Profile
import com.example.fitshare.User.User
import com.example.fitshare.User.UserLocation

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.fitshare.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.Marker
import io.realm.Realm
import io.realm.mongodb.sync.SyncConfiguration
import kotlinx.android.synthetic.main.recipe_view.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private var user: io.realm.mongodb.User? = null

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private lateinit var lastLocation: Location

    private lateinit var partition: String

    private lateinit var mapRealm: Realm
    private lateinit var profileRealm: Realm
    private lateinit var username: String

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onStart() {
        super.onStart()
        user = fitApp.currentUser()



        partition = "location"
        val config = SyncConfiguration.Builder(user!!, partition)
            .build()
        // this@MapsActivity.mapRealm = realm
        // Sync all realm changes via a new instance, and when that instance has been successfully created connect it to an on-screen list (a recycler view)
        Realm.getInstanceAsync(config, object : Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                // since this realm should live exactly as long as this activity, assign the realm to a member variable
                this@MapsActivity.mapRealm = realm
                // setUpRecyclerView(realm, user, partition)
            }
        })

        val profile_config : SyncConfiguration =
            SyncConfiguration.Builder(user!!, "Profile")
                .build()

        Realm.getInstanceAsync(profile_config, object: Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                // since this realm should live exactly as long as this activity, assign the realm to a member variable
                this@MapsActivity.profileRealm = realm
                val profile = profileRealm.where(Profile::class.java).
                equalTo("userid", user?.id.toString()).findFirst()
                username = profile?.username.toString()

            }
        })
    }


    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        // 1
        map.isMyLocationEnabled = true

// 2
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            // Got last known location. In some rare situations this can be null.
            // 3
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)

                val name =
                    mapRealm.where(Profile::class.java).equalTo("userid", user?.id.toString())
                        .findFirst()

//                val test = user?.profile?.firstName;


                mapRealm.where(Profile::class.java).equalTo("userid", user?.id.toString())
                    .findFirst()
                    ?.let { Log.i("Maps", it.firstName) }


                var currentLoc =
                    UserLocation(user.toString(), location.latitude, location.longitude)
                mapRealm.executeTransactionAsync { realm -> realm.insert(currentLoc) }


//                var currentLoc =
//                    user?.profile?.firstName?.let { UserLocation(it,location.latitude,location.longitude) }
//                mapRealm.executeTransactionAsync{realm -> realm.insert(currentLoc)}


                val locationsQuery = mapRealm.where(UserLocation::class.java).findAll()

//                for(loc in locationsQuery.indices){
//                    Log.e("Long",locationsQuery[loc])
//                }

                Log.i("Maps", locationsQuery[0]?.latitude.toString())


                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))

                //  map.addMarker(MarkerOptions().position(LatLng(33.8,-118.03)).title("TEST"))

                for (loc in locationsQuery) {
                    map.addMarker(
                        MarkerOptions()
                            .position(LatLng(loc.latitude, loc.longitude))
                            .title("USER MARKER")
                    )
                }

            }
        }

        // retrieve markers in db and add user profile name to object


    }


    override fun onMarkerClick(marker: Marker): Boolean
    {
        val ok = 5

        val dialogBuilder = AlertDialog.Builder(this)

        // set message of alert dialog
        dialogBuilder.setMessage("Do you want to go to User's profile ?")
            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton("Proceed", DialogInterface.OnClickListener {
                    dialog, id -> finish()
            })
            // negative button text and action
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
       // alert.setTitle("")
        // show alert dialog
        alert.show()

        return false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap




        map.getUiSettings().setZoomControlsEnabled(true)
        map.setOnMarkerClickListener(this)

        this.setUpMap()

    }

}

