package uk.ac.tees.mad.travelr.data.models.cities


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("address")
    val address: Address?,
    @SerializedName("geoCode")
    val geoCode: GeoCode?,
    @SerializedName("iataCode")
    val iataCode: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("subType")
    val subType: String?,
    @SerializedName("type")
    val type: String?
)