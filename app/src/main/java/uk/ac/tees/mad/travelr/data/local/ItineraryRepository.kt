package uk.ac.tees.mad.travelr.data.local

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uk.ac.tees.mad.travelr.data.models.destinations.DestinationData

class ItineraryRepository(
    private val dao: ItineraryDao,
    private val firestore: FirebaseFirestore= FirebaseFirestore.getInstance(),
    private val auth: FirebaseAuth= FirebaseAuth.getInstance()
) {

    private val collection="itineraies"

    suspend fun insertItinerary(destination: DestinationData) {
        return withContext(Dispatchers.IO) {
            val entity = ItineraryEntity.fromDestination(destination)
            dao.insert(entity)
        }
    }

    suspend fun deleteItinerary(id: String) {
        return withContext(Dispatchers.IO) {
            dao.delete(id)
        }
    }

    suspend fun getAllItineraries(): List<DestinationData> {
        return withContext(Dispatchers.IO) {
            dao.getAll().map {
                it.toDestination()
            }
        }
    }

    suspend fun isInItinerary(id: String): Boolean {
        return withContext(Dispatchers.IO) {
            dao.getItemById(id) != null
        }
    }
}