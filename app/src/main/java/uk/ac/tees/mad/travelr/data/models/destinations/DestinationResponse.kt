package uk.ac.tees.mad.travelr.data.models.destinations


import com.google.gson.annotations.SerializedName

data class DestinationResponse(
    @SerializedName("data")
    val data: List<DestinationData?>?,
    @SerializedName("meta")
    val meta: Meta?
)