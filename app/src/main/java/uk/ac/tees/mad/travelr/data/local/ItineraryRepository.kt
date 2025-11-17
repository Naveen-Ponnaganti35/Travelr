package uk.ac.tees.mad.travelr.data.local

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import uk.ac.tees.mad.travelr.data.models.destinations.DestinationData
import java.lang.Exception
import java.util.UUID

class ItineraryRepository(
    private val dao: ItineraryDao,
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {

    // collection user, cities, itineary
    // itenaries/itenary1 foreign key user-id    // user/itenearies/1    // user/itenearies/2    // user/itenearies/3//    private val collectionName = "itineraries"
//    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()


//    val userItineraries = FirebaseFirestore.getInstance()
//        .collection("users")
//        .document(auth.currentUser?.uid ?: UUID.randomUUID().toString())
//        .collection("itineraries")

    private val firestore= FirebaseFirestore.getInstance()
    private fun getUserItinerariesCollection()=firestore.collection("users")
        .document(auth.currentUser?.uid ?: UUID.randomUUID().toString())
        .collection("itineraries")




    suspend fun insertItinerary(destination: DestinationData) {

        return withContext(Dispatchers.IO) {

            val entity= ItineraryEntity.fromDestination(destination)
            dao.insert(entity)

            val docId=entity.id.ifEmpty {
                UUID.randomUUID().toString()
            }
            getUserItinerariesCollection().document(docId)
                .set(entity)

//            // to save for room
//            val entity = ItineraryEntity.fromDestination(destination)
//            dao.insert(entity)
//
//            // to save to the firestore
//            val docId = entity.id.ifEmpty { UUID.randomUUID().toString() }
//            userItineraries
//                .document(docId)
//                .set(entity)
        }

        // Save to Firestore
//        val userId = getUserId()
//        firestore.collection(collectionName)
//            .document(userId)
//            .collection("userItineraries")
//            .document(destination.id!!)
//            .set(destination)
    }

    suspend fun deleteItinerary(id: String) {
        return withContext(Dispatchers.IO) {
            // deleted from the room
            dao.delete(id)


            getUserItinerariesCollection().document(id).delete()
//            userItineraries.document(id)
//                .delete()
        }

        // Delete from Firestore
//        val userId = getUserId()
//        firestore.collection(collectionName)
//            .document(userId)
//            .collection("userItineraries")
//            .document(id)
//            .delete()
    }

    suspend fun getAllItineraries(): List<DestinationData> {
        return withContext(Dispatchers.IO) {
            try {
                getUserItinerariesCollection().get().await().documents.mapNotNull {
                    val entity = it.toObject(ItineraryEntity::class.java)
                    entity?.let {
                        if (!dao.itemAlreadyExists(it.id)) dao.insert(it)
                    }
                    entity
                }
            } catch (e: Exception) {}

            dao.getAll().map { it.toDestination() } // final list from Room
//            val localJob = async { dao.getAll().map { it.toDestination() } }
//            launch {
//                try {
//                    val remoteJobs=userItineraries.get().await().documents.mapNotNull {
//                        val entity=it.toObject(ItineraryEntity::class.java)
//                        entity?.let {
//                            if(!dao.itemAlreadyExists(it.id)){
//                                dao.insert(it)
//                            }
//                        }
//                        entity
//                    }
//                }catch (e: Exception){
//
//                }
//            }
//            localJob.await()
//            val remoteDestinations = userItineraries.get().await().documents.mapNotNull {
//                // firestore model to local room entity
//                val destinationData = it.toObject(ItineraryEntity::class.java)
//
//                destinationData?.let { destinationItem ->
//                    // item exists in firestore but not in local database, can happen on fresh install
//                    if (!dao.itemAlreadyExists(destinationItem.id)) {
//                        // insert new item to room database
//                        dao.insert(destinationItem)
//                    }
//                }
//                destinationData
//            }
//
//            // no internet available
//            dao.getAll().map {
//                it.toDestination()
//            }
        }
//        val userId = getUserId()
//        val cloudList = firestore.collection(collectionName)
//            .document(userId)
//            .collection("userItineraries")
//            .get()
//            .await()
//            .documents
//            .mapNotNull { it.toObject(DestinationData::class.java) }
//
//        // Merge local and cloud lists (optional: remove duplicates)
//        val all = (localList + cloudList).distinctBy { it.id }
//        return all
    }

    suspend fun isInItinerary(id: String): Boolean {
        return withContext(Dispatchers.IO) {
            dao.getItemById(id) != null
        }
    }

    suspend fun deleteAllItineraries(){
        return withContext(Dispatchers.IO){
            dao.deleteAllItineraries()
        }
    }

    suspend fun getLatestItinerary(): ItineraryEntity?{
        return dao.getLatestItinerary()
    }
}