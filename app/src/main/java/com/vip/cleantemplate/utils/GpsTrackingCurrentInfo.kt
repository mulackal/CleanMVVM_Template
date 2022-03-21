package com.vip.cleantemplate.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import java.util.*

class GpsTrackingCurrentInfo : LocationListener {
    override fun onLocationChanged(location: Location) {}
    override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {}
    override fun onProviderEnabled(s: String) {}
    override fun onProviderDisabled(s: String) {}

    companion object {
        // flag for GPS status
        var isGPSEnabled = false
        var ar: ArrayList<String>? = null

        // flag for network status
        var isNetworkEnabled = false
        var address: String? = null
        var location // location
                : Location? = null
        var latitude = 0.0 // latitude
        var longitude = 0.0 // longitude

        // Declaring a Location Manager
        protected var locationManager: LocationManager? = null
        fun sendCurrentTrackingInfo(
            context: Context,
            from: String
        ): HashMap<String?, String?>? {
            var targetLocation: Location? = null
            var mValues =
                HashMap<String?, String?>()
            try {
                var adddres_string = ""
                val update_time = System.currentTimeMillis()
                if (latitude == 0.0 && longitude == 0.0) {
                    val location =
                        getLocation(context)
                    Log.e("forgps", "lat inside$from")
                    latitude = location!!.latitude
                    longitude = location.longitude
                }
                targetLocation = Location("") //provider name is unnecessary
                targetLocation.latitude = latitude //your coords of course
                targetLocation.longitude = longitude
                try {
                    if (latitude != 0.0 && longitude != 0.0) getAddress(
                        context,
                        latitude,
                        longitude
                    ) // this use get the address
                } catch (e: Exception) {
                    if (mValues != null) {
                        mValues.clear()
                        mValues["Latitude"] = latitude.toString()
                        mValues["Longitude"] = longitude.toString()
                        mValues["Address"] = adddres_string
                        mValues["Track_time"] = update_time.toString()
                        return mValues
                    }
                    e.printStackTrace()
                }
                try {
                    adddres_string = ar.toString()
                    adddres_string = adddres_string.replace("\\[".toRegex(), " ")
                    adddres_string = adddres_string.replace("\\]".toRegex(), " ")
                    adddres_string = adddres_string.trim { it <= ' ' }
                    Log.e("address : ", "" + adddres_string)
                } catch (e: Exception) {
                    if (mValues != null) {
                        mValues.clear()
                        mValues["Latitude"] = latitude.toString()
                        mValues["Longitude"] = longitude.toString()
                        mValues["Address"] = adddres_string
                        mValues["Track_time"] = update_time.toString()
                        return mValues
                    }
                    e.printStackTrace()
                }
                if (mValues != null) {
                    mValues.clear()
                    mValues["Latitude"] = latitude.toString()
                    mValues["Longitude"] = longitude.toString()
                    mValues["Address"] = adddres_string
                    mValues["Track_time"] = update_time.toString()
                } else {
                    mValues = HashMap()
                    mValues.clear()
                    mValues["Latitude"] = latitude.toString()
                    mValues["Longitude"] = longitude.toString()
                    mValues["Address"] = adddres_string
                    mValues["Track_time"] = update_time.toString()
                }
                Log.d(
                    "forgps", "infoclass: long " + longitude
                            + " latti " + latitude
                )
            } catch (e: NullPointerException) {
                Log.d("forgps", "onReceive: error")
                e.printStackTrace()
            }
            return mValues
        }

        private fun getAddress(
            context: Context,
            latitude: Double,
            longitude: Double
        ) {
            try {
                val geocoder = Geocoder(context, Locale.getDefault())
                val addresses =
                    geocoder.getFromLocation(latitude, longitude, 1)
                var sublocality = ""
                var city = ""
                var state = ""
                var zip = ""
                var country = ""
                try {
                    try {
                        sublocality = addresses[0].subLocality
                    } catch (e: Exception) {
                        city = addresses[0].locality
                        state = addresses[0].adminArea
                        zip = addresses[0].postalCode
                        country = addresses[0].countryName
                    }
                    city = addresses[0].locality
                    state = addresses[0].adminArea
                    zip = addresses[0].postalCode
                    country = addresses[0].countryName
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                address = null
                address =
                    sublocality + "," + city + "," + state + "," +
                            zip + "," + country
                ar = ArrayList()
                ar!!.clear()
                ar!!.add(sublocality)
                ar!!.add(city)
                ar!!.add(state)
                ar!!.add(zip)
                ar!!.add(country)
                Log.e(
                    "location netwrk :",
                    "geo : " + address + "*long* " + location!!.longitude + "*lat* " + location!!.latitude
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun getLocation(context: Context): Location? {
            try {
                locationManager =
                    context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

                // getting GPS status
                isGPSEnabled =
                    locationManager!!
                        .isProviderEnabled(LocationManager.GPS_PROVIDER)

                // getting network status
                isNetworkEnabled =
                    locationManager!!
                        .isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                if (!isGPSEnabled && !isNetworkEnabled) {
                    // no network provider is enabled
                } else {

                    // First get location from Network Provider
                    if (isNetworkEnabled) {
                        if (ActivityCompat.checkSelfPermission(
                                context,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) !=
                            PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(
                                context,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                            != PackageManager.PERMISSION_GRANTED
                        ) {

                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return null
                        }
                        Log.d("forgps", "Network")
                        if (locationManager != null) {
                            location =
                                locationManager!!
                                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                            //  location.setAccuracy(Criteria.ACCURACY_HIGH);
                            if (location != null) {
                                latitude =
                                    location!!.latitude
                                longitude =
                                    location!!.longitude
                            }
                        }
                    }
                    // if GPS Enabled get lat/long using GPS Services
                    if (isGPSEnabled) {
                        if (location == null) {
                            if (ActivityCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                ) !=
                                PackageManager.PERMISSION_GRANTED &&
                                ActivityCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                )
                                != PackageManager.PERMISSION_GRANTED
                            ) {

                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return null
                            }
                            Log.d("forgps", "GPS Enabled")
                            if (locationManager != null) {
                                location =
                                    locationManager!!
                                        .getLastKnownLocation(LocationManager.GPS_PROVIDER)
                                if (location != null) {
                                    latitude =
                                        location!!.latitude
                                    longitude =
                                        location!!.longitude
                                }
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return location
        }
    }
}