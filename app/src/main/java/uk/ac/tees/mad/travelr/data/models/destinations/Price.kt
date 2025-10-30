package uk.ac.tees.mad.travelr.data.models.destinations


import com.google.gson.annotations.SerializedName

data class Price(
    @SerializedName("amount")
    val amount: String?,
    @SerializedName("currencyCode")
    val currencyCode: String?
)