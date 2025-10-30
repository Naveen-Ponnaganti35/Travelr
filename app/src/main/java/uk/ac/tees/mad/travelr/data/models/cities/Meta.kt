package uk.ac.tees.mad.travelr.data.models.cities


import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("links")
    val links: Links?
)