package uk.ac.tees.mad.travelr.data.models.cities


import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("countryCode")
    val countryCode: String?,
    @SerializedName("stateCode")
    val stateCode: String?
)