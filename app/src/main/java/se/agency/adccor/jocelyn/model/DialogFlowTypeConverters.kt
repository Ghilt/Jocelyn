package se.agency.adccor.jocelyn.model

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import se.agency.adccor.jocelyn.model.network.dialogFlowData.DialogFlowContext
import se.agency.adccor.jocelyn.model.network.dialogFlowData.DialogFlowMessage


/**
 * Created by adamn on 12/20/2017.
 */
class DialogFlowTypeConverters {

    @TypeConverter
    fun stringToDialogFlowMessageList(json: String): ArrayList<DialogFlowMessage> {
        val type = object : TypeToken<ArrayList<DialogFlowMessage>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun dialogFlowMessageListToString(list: ArrayList<DialogFlowMessage>): String {
        val type = object : TypeToken<ArrayList<DialogFlowMessage>>() {}.type
        return Gson().toJson(list, type)
    }

    @TypeConverter
    fun stringToDialogFlowContextList(json: String): ArrayList<DialogFlowContext> {
        val type = object : TypeToken<ArrayList<DialogFlowContext>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun dialogFlowContextListToString(list: ArrayList<DialogFlowContext>): String {
        val type = object : TypeToken<ArrayList<DialogFlowContext>>() {}.type
        return Gson().toJson(list, type)
    }
}