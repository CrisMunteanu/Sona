package de.syntax_institut.androidabschlussprojekt.data.local.dao



import androidx.room.*
import de.syntax_institut.androidabschlussprojekt.data.local.entity.FavoriteEntity
import de.syntax_institut.androidabschlussprojekt.data.local.entity.MeditationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MeditationDao {

    @Query("SELECT * FROM meditations")
    fun getAll(): Flow<List<MeditationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: MeditationEntity)

    @Delete
    suspend fun delete(item: MeditationEntity)

    @Query("SELECT * FROM favorites")
    suspend fun getAllOnce(): List<FavoriteEntity>
}