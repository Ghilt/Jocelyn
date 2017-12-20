package se.agency.adccor.jocelyn.model.network.dialogFlowData

import android.arch.persistence.room.Embedded

/**
 * Created by adamn on 12/12/2017.
 */

data class DialogFlowResult(
        val source: String,
        val resolvedQuery: String,
        val action: String,
        val actionIncomplete: String,
        //parameters //contains intent specific parameters
        val contexts: ArrayList<DialogFlowContext>,
        @Embedded
        val metadata: DialogFlowMetaData?,
        @Embedded
        val fulfillment: DialogFlowFulfillment,
        val score: Float)