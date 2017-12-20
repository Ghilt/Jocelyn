package se.agency.adccor.jocelyn.model.network.dialogFlowData

/**
 * Created by adamn on 12/20/2017.
 */
data class DialogFlowMetaData(val intentId: String,
                              val webHookUsed: String?,
                              val webhookForSlotFillingUsed: String,
                              val intentName: String)