    package uk.ac.tees.mad.travelr.data.local.user

    import androidx.room.Dao
    import androidx.room.Delete
    import androidx.room.Insert
    import androidx.room.OnConflictStrategy
    import androidx.room.Query
    import androidx.room.Update
    import uk.ac.tees.mad.travelr.data.models.user.UserProfileEntity


    @Dao
    interface UserProfileDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertUser(user: UserProfileEntity)

        @Update
        suspend fun updateUser(user: UserProfileEntity)

//        @Query("SELECT * FROM user_profile WHERE userId = :id")
//        suspend fun getUserById(id: String): UserProfileEntity

        @Query("select * from user_profile limit 1")
        suspend fun getCurrentUser(): UserProfileEntity?

        @Query("DELETE FROM user_profile WHERE userId = :id")
        suspend fun deleteUserById(id: String)


        @Query("delete from user_profile")
        suspend fun deleteAllUser()
    }