package de.syntax_institut.androidabschlussprojekt.data.local.entity



import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "journal_entries")
data class JournalEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)