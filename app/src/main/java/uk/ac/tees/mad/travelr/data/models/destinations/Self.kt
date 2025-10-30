package uk.ac.tees.mad.travelr.data.models.destinations


import com.google.gson.annotations.SerializedName

data class Self(
    @SerializedName("href")
    val href: String?,
    @SerializedName("methods")
    val methods: List<String?>?
)