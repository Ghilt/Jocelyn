package se.agency.adccor.jocelyn.model.network

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.os.AsyncTask
import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import retrofit2.Response
import java.lang.ref.WeakReference

abstract class NetworkBoundResource<ResultType, RequestType> {

    private val result = MediatorLiveData<Resource<ResultType?>>()

    // Called to save the result of the API response into the database
    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType?)

    // Called with the data in the database to decide whether it should be
    // fetched from the network.
    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    // Called to get the cached data from the database
    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    // Called to create the API call.
    @MainThread
    protected abstract fun createCall(): LiveData<Response<RequestType>>

    // Called when the fetch fails. The child class may want to reset components
    // like rate limiter.
    @MainThread
    protected fun onFetchFailed() {
    }

    // returns a LiveData that represents the resource, implemented
    // in the base class.
    fun getAsLiveData(): LiveData<Resource<ResultType?>> {
        result.value = Resource.loading(null)
        val dbSource = loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    result.setValue(Resource.success(newData))
                }
            }
        }
        return result
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()
        // we re-attach dbSource as a new source,
        // it will dispatch its latest value quickly
        result.addSource(dbSource) { newData ->
            result.setValue(Resource.loading(newData)) }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)

            if (response?.isSuccessful == true) {
                saveResultAndReInit(response)
            } else {
                onFetchFailed()
                result.addSource(dbSource) { newData ->
                    result.setValue(Resource.error(response?.message() ?: "Network fetch failed", newData))
                }
            }
        }
    }

    @MainThread
    private fun saveResultAndReInit(response: Response<RequestType>) {
        MyTask(this, response).execute()
    }

    private class MyTask<ResultType, RequestType>(source: NetworkBoundResource<ResultType, RequestType>, response: Response<RequestType>) : AsyncTask<Void, Void, Void>() {

        private val sourceReference: WeakReference<NetworkBoundResource<ResultType, RequestType>> = WeakReference(source)
        private val responseReference: WeakReference<Response<RequestType>> = WeakReference(response)

        override fun doInBackground(vararg params: Void): Void? {

            val source = sourceReference.get()
            val response = responseReference.get()
            if (source != null && response != null) {
                source.saveCallResult(response.body())
            }

            return null
        }

        override fun onPostExecute(result: Void?) {
            val source = sourceReference.get()
            source?.result?.addSource(source.loadFromDb()) { newData ->
                source.result.setValue(Resource.success(newData))
            }

        }
    }


}