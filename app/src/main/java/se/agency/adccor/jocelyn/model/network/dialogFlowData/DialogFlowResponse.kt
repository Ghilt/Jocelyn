package se.agency.adccor.jocelyn.model.network.dialogFlowData

import android.arch.persistence.room.Embedded

/**
 * Created by adamn on 12/9/2017.
 */
data class DialogFlowResponse(
        val id: String,
        val timestamp: String,
        val lang: String,
        @Embedded
        val result: DialogFlowResult,
        @Embedded
        val status: DialogFlowStatus,
        val sessionId: String
) {
        fun getChatMessage(): String = result.fulfillment.speech
}