package uk.ac.tees.mad.travelr.data.models.destinations


import com.google.gson.annotations.SerializedName

data class DestinationData(
    @SerializedName("bookingLink")
    val bookingLink: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("shortDescription")  // Add this line
    val shortDescription: String?,
    @SerializedName("geoCode")
    val geoCode: GeoCode?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("minimumDuration")
    val minimumDuration: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("pictures")
    val pictures: List<String?>?,
    @SerializedName("price")
    val price: Price?,
    @SerializedName("self")
    val self: Self?,
    @SerializedName("type")
    val type: String?
)