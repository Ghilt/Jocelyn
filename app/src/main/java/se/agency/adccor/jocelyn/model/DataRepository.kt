package se.agency.adccor.jocelyn.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.LivePagedListProvider
import android.os.AsyncTask
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import se.agency.adccor.jocelyn.model.network.DialogFlowMessage
import se.agency.adccor.jocelyn.model.network.DialogFlowWebService
import se.agency.adccor.jocelyn.model.network.NetworkBoundResource
import se.agency.adccor.jocelyn.model.network.Resource
import se.agency.adccor.jocelyn.views.dLog


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

//    fun getChatResponse(message: String): LiveData<DialogFlowMessage> {
//        val call = service.testQuery()
//        val data = MutableLiveData<DialogFlowMessage>()
//        call.enqueue(object : Callback<DialogFlowMessage> {
//
//            override fun onResponse(call: Call<DialogFlowMessage>?, response: Response<DialogFlowMessage>?) {
//                data.value = DialogFlowMessage("Hi there 192")
//                dLog("success")
//            }
//
//            override fun onFailure(call: Call<DialogFlowMessage>?, t: Throwable?) {
//                dLog("fail")
//            }
//
//        })
//        return data
//    }

    fun getChatResponse(message: String): LiveData<Resource<DialogFlowMessage?>> {
        return object : NetworkBoundResource<DialogFlowMessage, DialogFlowMessage>() {

            override fun saveCallResult(item: DialogFlowMessage?) {
                if (item?.result?.resolvedQuery != null) {
                    insertNewMessage(ChatMessage(0, item.result.resolvedQuery))
                }
            }

            override fun shouldFetch(data: DialogFlowMessage?) = true

            override fun loadFromDb(): LiveData<DialogFlowMessage> = MutableLiveData<DialogFlowMessage>().apply { value = null }

            override fun createCall(): LiveData<Response<DialogFlowMessage>> {
                val call = service.testQuery()
                val data = MutableLiveData<Response<DialogFlowMessage>>()
                call.enqueue(object : Callback<DialogFlowMessage> {

                    override fun onResponse(call: Call<DialogFlowMessage>?, response: Response<DialogFlowMessage>?) {
                        data.value = response
                        dLog("success")
                    }

                    override fun onFailure(call: Call<DialogFlowMessage>?, t: Throwable?) {
                        data.value = null
                        dLog("fail")
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