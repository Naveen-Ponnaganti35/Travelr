package uk.ac.tees.mad.travelr.data.models.cities


import com.google.gson.annotations.SerializedName

data class CitiesResponse(
    @SerializedName("data")
    val data: List<Data?>?,
    @SerializedName("meta")
    val meta: Meta?,
    @SerializedName("warnings")
    val warnings: List<Warning?>?
)