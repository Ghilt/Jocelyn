package se.agency.adccor.jocelyn.model.network.dialogFlowData

import java.util.*

/**
 * Created by adamn on 12/13/2017.
 */
data class DialogFlowFulfillment(val speech: String,
                                 val messages: ArrayList<DialogFlowMessage>
)