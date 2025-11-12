package uk.ac.tees.mad.travelr.data.models.cities


import com.google.gson.annotations.SerializedName

data class Warning(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("detail")
    val detail: String?,
    @SerializedName("source")
    val source: Source?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("title")
    val title: String?
)