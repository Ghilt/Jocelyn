package se.agency.adccor.jocelyn.model.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by adamn on 12/9/2017.
 */
interface DialogFlowWebService {

    @GET("/users/{user}/repos")
    fun reposForUser(@Path("user")user: String) : Call<List<DialogFlowMessage>>

    @GET("/users/{user}/repos")
    fun reposForUser2(@Path("user")user: String) : Call<DialogFlowMessage>
}