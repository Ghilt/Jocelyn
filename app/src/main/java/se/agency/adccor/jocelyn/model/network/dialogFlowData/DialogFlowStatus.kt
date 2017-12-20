package se.agency.adccor.jocelyn.model.network.dialogFlowData

/**
 * Created by adamn on 12/20/2017.
 */

data class DialogFlowStatus(val code: Int,
                            val errorType: String,
                            val webHookTimeout: Boolean)