package uk.ac.tees.mad.travelr.data.models.user

import android.util.Log
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import uk.ac.tees.mad.travelr.data.local.user.UserProfileDao
import java.util.UUID

class ProfileRepository(
    private val userDao: UserProfileDao,
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

) {

    private val firestore = FirebaseFirestore.getInstance()

    private fun getUserDocument() = firestore.collection("users")
        .document(auth.currentUser?.uid ?: UUID.randomUUID().toString())

    suspend fun deleteUserAccountPermanently(
        email: String = auth.currentUser!!.email!!,
        password: String
    ) {
        withContext(Dispatchers.IO) {
            // delete from the room
//            val user=auth.currentUser
//            // delete itenary first
//            user?.delete()?.await()

            // delete from the firestore
            val uid = auth.currentUser?.uid
            if (uid == null) {
                return@withContext
            }
            // 🔹 Step 1: Reauthenticate (required before delete)
            val credential = EmailAuthProvider.getCredential(email, password)
            try {
                auth.currentUser!!.reauthenticate(credential).await()
            } catch (e: Exception) {
                throw Exception("Re-authentication failed: ${e.message}")
            }
            userDao.deleteAllUser()
            // delete user profile from firebase
            firestore.collection("users")
                .document(uid)
                .delete()
                .await()

            val itinerariesRef = firestore.collection("users")
                .document(uid)
                .collection("itineraries")

            val itineraries = itinerariesRef.get().await()
            itineraries.documents.forEach { doc ->
                doc.reference.delete().await()
            }


            //Delete Firebase Auth account (must be last)
            auth.currentUser?.delete()?.await()

        }

    }

    suspend fun insertUser(user: UserProfile) {
        withContext(Dispatchers.IO) {
            userDao.insertUser(user.toEntity())
            getUserDocument().set(user).await()
        }
    }

    suspend fun updateUser(user: UserProfile) {
        withContext(Dispatchers.IO) {
            userDao.updateUser(user.toEntity())
            getUserDocument().set(user).await()
        }
    }

    suspend fun deleteLocalUser() {
        withContext(Dispatchers.IO) {
            userDao.deleteAllUser()
        }
    }


    suspend fun getCurrentUser(): UserProfile {
        return withContext(Dispatchers.IO) {

            val localUser = userDao.getCurrentUser()
            if (localUser != null) {
                localUser.toUserProfile()
            } else {
                val remoteUser = fetchUserFromFirestore()
                Log.d("Hello", "getCurrentUser: $remoteUser ")
                userDao.insertUser(remoteUser.toEntity())
                remoteUser
            }


        }
        Log.d("Hello", "getCurrentUser:  PR ${userDao.getCurrentUser()}")
    }

    private suspend fun fetchUserFromFirestore(): UserProfile {
        val uid = auth.currentUser?.uid ?: return UserProfile()
        val docRef = firestore.collection("users").document(uid)
        return try {
            val snapshot = docRef.get().await()
            snapshot.toObject(UserProfile::class.java) ?: UserProfile()
        } catch (e: Exception) {
            e.printStackTrace()
            UserProfile()
        }
    }


}

