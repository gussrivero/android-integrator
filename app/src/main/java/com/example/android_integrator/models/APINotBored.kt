package com.example.android_integrator.models

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface APINotBored {
    @GET("activity")
    suspend fun getRandom():Response<NotBoredResponse>
    @GET("activity")
    suspend fun getActivitiesByParticipantsAndType(@Query("type") type:String, @Query("participants")participants:Int): Response<NotBoredResponse>
    @GET("activity")
    suspend fun getActivitiesByType(@Query("type")type:String): Response<NotBoredResponse>
    @GET("activity")
    suspend fun getActivitiesByParticipants(@Query("participants")participants: Int): Response<NotBoredResponse>


}