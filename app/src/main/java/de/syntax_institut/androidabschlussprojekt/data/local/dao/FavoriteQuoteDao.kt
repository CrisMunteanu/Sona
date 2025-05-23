package de.syntax_institut.androidabschlussprojekt.data.local.dao

import androidx.room.*
import de.syntax_institut.androidabschlussprojekt.data.local.entity.FavoriteQuoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteQuoteDao {
    @Query("SELECT * FROM favorite_quotes")
    fun getAll(): Flow<List<FavoriteQuoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(quote: FavoriteQuoteEntity)

    @Delete
    suspend fun delete(quote: FavoriteQuoteEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_quotes WHERE text = :text AND author = :author)")
    suspend fun isFavorite(text: String, author: String): Boolean
}