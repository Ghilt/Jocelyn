package se.agency.adccor.jocelyn.model.network


/**
 * Created by adamn on 12/9/2017.
 */
//a generic class that describes a data with a status
data class Resource<T> private constructor(val status: Status, val data : T?, val message :String?) {

    companion object {

        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }

    enum class Status {
        SUCCESS, ERROR, LOADING
    }
}