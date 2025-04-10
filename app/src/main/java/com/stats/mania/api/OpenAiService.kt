package com.stats.mania.api

import com.stats.mania.model.ChatRequest
import com.stats.mania.model.ChatResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface OpenAiService {

    @GET("v2/country/{country}/indicator/{id}?format=json")
    suspend fun getCountryIndicatorDetails(
        @Path("country") country: String,
        @Path("id") id: String,
        @Query("page") page: Int
    ): Response<List<Any>>

    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer "
    )
    @POST("v1/chat/completions")
    fun createChatCompletion(@Body request: ChatRequest): Call<ChatResponse>



}