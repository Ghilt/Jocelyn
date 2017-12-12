package se.agency.adccor.jocelyn.model.network

import retrofit2.Call
import retrofit2.http.*

/**
 * Created by adamn on 12/9/2017.
 */
interface DialogFlowWebService {

//    @Headers("Authorization: Bearer cdfb636b6b7341c99c448c64d090577e") // TODO store client access token properly
    @GET("/v1/query")
    fun testQuery(
        @Header("Authorization") clientAccessToken: String = "Bearer cdfb636b6b7341c99c448c64d090577e",
        @Query("v") version: String = "20171212",
        @Query("lang") lang: String = "en",
        @Query("query") query: String = "Where%20do%20you%20come%20fr0m%3F",
        @Query("sessionId") sessionId: String = "12345678904"
        ) : Call<DialogFlowMessage>

    @GET("/users/{user}/repos")
    fun reposForUser2(@Path("user")user: String) : Call<DialogFlowMessage>
}