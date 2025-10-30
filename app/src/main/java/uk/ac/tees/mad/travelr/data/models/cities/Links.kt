package uk.ac.tees.mad.travelr.data.models.cities


import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("self")
    val self: String?
)