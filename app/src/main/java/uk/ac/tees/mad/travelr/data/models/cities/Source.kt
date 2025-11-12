package uk.ac.tees.mad.travelr.data.models.cities


import com.google.gson.annotations.SerializedName

data class Source(
    @SerializedName("parameter")
    val parameter: String?
)