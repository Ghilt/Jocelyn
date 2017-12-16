package se.agency.adccor.jocelyn.model.network

import retrofit2.Call
import retrofit2.http.*

/**
 * Created by adamn on 12/9/2017.
 */
interface DialogFlowWebService {

    @GET("/v1/query")
    fun testQuery(@Query("query") query: String) : Call<DialogFlowMessage>

    @GET("/users/{user}/repos")
    fun reposForUser2(@Path("user")user: String) : Call<DialogFlowMessage>
}