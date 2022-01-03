package com.example.android_integrator.models

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APINotBored {
    @GET
    suspend fun getActivitiesByType(@Url url:String): Response<NotBoredResponse>
    @GET
    suspend fun getActivitiesByParticipants(@Url url: String): Response<NotBoredResponse>
    @GET
    suspend fun getActivitiesByParticipantsAndType(@Url url: String): Response<NotBoredResponse>


}