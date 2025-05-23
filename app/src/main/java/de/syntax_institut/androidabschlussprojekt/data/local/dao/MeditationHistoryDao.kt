package de.syntax_institut.androidabschlussprojekt.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.syntax_institut.androidabschlussprojekt.data.local.entity.MeditationHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MeditationHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(meditation: MeditationHistoryEntity)

    @Query("SELECT * FROM meditation_history ORDER BY timestamp DESC")
    fun getAll(): Flow<List<MeditationHistoryEntity>>

    @Delete
    suspend fun delete(meditation: MeditationHistoryEntity)

    @Query("DELETE FROM meditation_history")
    suspend fun clear()
}