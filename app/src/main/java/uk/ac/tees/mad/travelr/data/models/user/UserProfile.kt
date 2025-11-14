package uk.ac.tees.mad.travelr.data.models.user

import androidx.room.Entity
import androidx.room.PrimaryKey

data class UserProfile(

    val userId:String="",
    val fullName:String="",
    val email:String="",
    val preferences:UserPreferences=UserPreferences()
){

}

data class UserPreferences(
    val favoriteDestinationType:String="Historical",
    val currency:String="USD",
    val notificationEnabled: Boolean=true,
    val emailUpdatesEnabled:Boolean=true,
)

// UserProfileEntity.kt
@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val userId: String,
    val fullName: String,
    val email: String,
    val favoriteDestinationType: String = "Historical",
    val currency: String = "USD",
    val notificationEnabled: Boolean = true,
    val emailUpdatesEnabled: Boolean = true
)
// Mapping functions
fun UserProfile.toEntity() = UserProfileEntity(
    userId = userId,
    fullName = fullName,
    email = email,
    favoriteDestinationType = preferences.favoriteDestinationType,
    currency = preferences.currency,
    notificationEnabled = preferences.notificationEnabled,
    emailUpdatesEnabled = preferences.emailUpdatesEnabled
)

fun UserProfileEntity.toUserProfile() = UserProfile(
    userId = userId,
    fullName = fullName,
    email = email,
    preferences = UserPreferences(
        favoriteDestinationType = favoriteDestinationType,
        currency = currency,
        notificationEnabled = notificationEnabled,
        emailUpdatesEnabled = emailUpdatesEnabled
    )
)