package com.stats.mania.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("v2/topic?format=json")
    suspend fun getTopics(): Response<List<Any>>

    @GET("v2/topic/{id}/indicator?format=json")
    suspend fun getIndicators(
        @Path("id") id: String,
        @Query("page") page: Int

    ): Response<List<Any>>

    @GET("v2/country/all/indicator/{id}?format=json")
    suspend fun getIndicatorDetails(
        @Path("id") id: String,
        @Query("page") page: Int
    ): Response<List<Any>>

    @GET("v2/country?format=json")
    suspend fun getCountries(
        @Query("page") page: Int
    ): Response<List<Any>>


    @GET("v2/country/{country}/indicator/{id}?format=json")
    suspend fun getCountryIndicatorDetails(
        @Path("country") country: String,
        @Path("id") id: String,
        @Query("page") page: Int
    ): Response<List<Any>>


}