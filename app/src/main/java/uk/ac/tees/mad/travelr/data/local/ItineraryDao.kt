package uk.ac.tees.mad.travelr.data.local

import androidx.room.*


@Dao
interface ItineraryDao {

    // fix naming convention
    @Query("Select * from itineraries ")
    suspend fun getAll(): List<ItineraryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ItineraryEntity)

    @Query("Delete from itineraries where id=:id")
    suspend fun delete(id:String)

    @Query("Select * from itineraries where id=:id")
    suspend fun getItemById(id: String): ItineraryEntity?


    // to find 1 item from the iti with id equal to the said id
    @Query("SELECT EXISTS(SELECT 1 FROM itineraries WHERE id = :id)")
    suspend fun itemAlreadyExists(id: String): Boolean
}