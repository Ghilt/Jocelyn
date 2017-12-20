package se.agency.adccor.jocelyn.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import se.agency.adccor.jocelyn.model.network.dialogFlowData.DialogFlowResponse

/**
 * Created by adamn on 11/26/2017.
 */

@Entity(tableName = "message")
data class ChatMessage(
        @PrimaryKey(autoGenerate = true)
        val uid: Long,
        @Embedded
        val dialogFlowData: DialogFlowResponse? = null,
        @Embedded
        val userMessageData: UserMessage? = null
) {

    fun isBotMessage() = dialogFlowData != null

    val message: String?
        get() = dialogFlowData?.getChatMessage()?: userMessageData?.message

}