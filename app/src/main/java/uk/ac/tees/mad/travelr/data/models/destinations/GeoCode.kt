package uk.ac.tees.mad.travelr.data.models.destinations


import com.google.gson.annotations.SerializedName

data class GeoCode(
    @SerializedName("latitude")
    val latitude: Double?,
    @SerializedName("longitude")
    val longitude: Double?
)