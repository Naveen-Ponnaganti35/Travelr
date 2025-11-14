package uk.ac.tees.mad.travelr.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uk.ac.tees.mad.travelr.data.models.destinations.DestinationData
import java.util.UUID


@Entity(tableName = "itineraries")
data class ItineraryEntity(

    @PrimaryKey val id: String,
    val name: String?,
    val description: String?,
    val shortDescription: String?,
    val picture: String?,
    val bookingLink: String?,
//    val
//    val synced: Boolean = false // set to true in room db when item gets synced to firestore
) {
    companion   object {
        fun fromDestination(destination: DestinationData): ItineraryEntity {
            val picturesJson = Gson().toJson(destination.pictures ?: emptyList<String>())
            return ItineraryEntity(
                id = destination.id ?: UUID.randomUUID().toString(),
                name = destination.name,
                description = destination.description,
                shortDescription = destination.shortDescription,
                picture = picturesJson,
                bookingLink = destination.bookingLink
            )

        }


    }

    constructor() : this("", "", "", "", "", "")

    fun toDestination(): DestinationData {
        val type = object : TypeToken<List<String>>() {}.type
        val pics: List<String> = Gson().fromJson(picture, type)
        return DestinationData(
            id = id,
            name = name,
            description = description,
            shortDescription = shortDescription,
            pictures = pics,
            bookingLink = bookingLink,
            geoCode = null,
            minimumDuration = null,
            price = null,
            self = null,
            type = null
        )
    }
}