package se.agency.adccor.jocelyn.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by adamn on 11/26/2017.
 */

@Entity(tableName = "message")
data class ChatMessage(
    @PrimaryKey(autoGenerate = true)
    val uid: Long,
    val firstName: String = "",
    val lastName: String = ""
)