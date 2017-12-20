package se.agency.adccor.jocelyn.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.LivePagedListProvider
import android.os.AsyncTask
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import se.agency.adccor.jocelyn.model.network.DialogFlowWebService
import se.agency.adccor.jocelyn.model.network.NetworkBoundResource
import se.agency.adccor.jocelyn.model.network.Resource
import se.agency.adccor.jocelyn.model.network.dialogFlowData.DialogFlowResponse


/**
 * Created by adamn on 11/27/2017.
 */
class DataRepository(private val service: DialogFlowWebService, private val dao: ChatMessageDao) {

    fun messagesPaged(): LivePagedListProvider<Int, ChatMessage> = dao.getMessagesPaged()

    fun loadMessages(): LiveData<List<ChatMessage>> {
        return dao.getAllMessages()
    }

    fun insertNewMessage(chatMessage: ChatMessage) = exeggutor {
        dao.insert(chatMessage)
    }

    fun getChatResponse(message: String): LiveData<Resource<DialogFlowResponse?>> {
        return object : NetworkBoundResource<DialogFlowResponse, DialogFlowResponse>() {

            override fun saveCallResult(item: DialogFlowResponse?) {
                if (item?.result?.fulfillment?.speech != null) {
                    insertNewMessage(ChatMessage(0, item))
                }
            }

            override fun shouldFetch(data: DialogFlowResponse?) = true

            override fun loadFromDb(): LiveData<DialogFlowResponse> = MutableLiveData<DialogFlowResponse>().apply { value = null }

            override fun createCall(): LiveData<Response<DialogFlowResponse>> {
                val call = service.testQuery(query = message)
                val data = MutableLiveData<Response<DialogFlowResponse>>()
                call.enqueue(object : Callback<DialogFlowResponse> {

                    override fun onResponse(call: Call<DialogFlowResponse>?, response: Response<DialogFlowResponse>?) {
                        data.value = response
                    }

                    override fun onFailure(call: Call<DialogFlowResponse>?, t: Throwable?) {
                        Log.d("Network", "GetChatResponse network call failed ${t?.message}")
                        data.value = null
                    }

                })

                return data
            }

        }.getAsLiveData()

    }
}

/**Is AsyncTask worse than Executors.newSingleThreadExecutor() ? */
fun exeggutor(task: () -> Unit) {
    AsyncTask.execute {
        task.invoke()
    }
}

/**                            .'
 *                              | \
 *                              |  .
 *              '._             |   .
 *              `. `._          |   |        .             __...
 *                `.  `.        |   '      .'        _,.-"'_.'
 *                  .   `.      |    .    / '    _,-'   _,'
 *                   `.   `.    |        / /  _,"    _,"
 *           `+.._     `    `.  '     . / / ."     ,"
 *              `._`-._ `.    `. .    |/ /,'     .'
 *    _,..---""""--`.  `-.`.    \|    | ./     ,'  _,.---,________
 * -`=..__                `-.    |    |.'    .'_..+---"""         `"-..
 *        `""---..___        `.  |    |'   .'-"          ___,.....---""`'
 *               _,.-""__,.._  `   ___'  .'  ____..---""'
 *         _,.-""    .'  ,.  \ .-"'   `-.  ""-------...__
 *      .-"    __.-.'   '-"'  / -='   `"'\......__       `"-..._
 *    ." _,.--""  / ."""|    /            \  _  ..`.-.....______`_
 *   '.-' .'_.-"".  | _.|   .   `.-----"'  .'"  __  `             '
 *       -"'     '  |'  |   |              | '""     .
 *                \  ...'   |              '         |`-.
 *                 `._      ,.            /          '"--'
 *                    `""".'  `._     _,"`.       _,'
 *                       /.....__`"""'     \--..-"
 *                      /        `'""'----...
 *                .    /____                |
 *               | |  j----.`""---..__      |
 *         '`-.,-`.'--|`-.  `.        `'"--.|
 *          `./   ___ `.  .  |              '
 *           ' ,"'   `. . |  |             /
 *           . |      | | | .'          .'j
 *            \`.     | '-'`..._____..-'  |
 *             `.`.__.'/     ,'`._       ,'
 *               `"--`'     . \   `-.__ /
 *                          |  `...___.'
 *                          /"`__.._'".
 *                          `"'     `" mh
 */