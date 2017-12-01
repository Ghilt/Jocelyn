package se.agency.adccor.jocelyn.model

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListProvider
import android.os.AsyncTask

/**
 * Created by adamn on 11/27/2017.
 */
class DataRepository(private val dao: ChatMessageDao) {

    fun loadMessages() : LiveData<List<ChatMessage>> {
        return dao.getAllMessages()
    }

    fun insertNewMessage(chatMessage: ChatMessage)  = exeggutor {
            dao.insert(chatMessage)
    }

    fun messagesPaged(): LivePagedListProvider<Int, ChatMessage> = dao.getMessagesPaged()
}

/**Is AsyncTask worse than Executors.newSingleThreadExecutor() ? */
fun exeggutor(task: () -> Unit ){
    AsyncTask.execute{
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