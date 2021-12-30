package com.example.android_integrator.models

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APINotBored {
    @GET
    suspend fun getActivities(@Url url:String): Response<NotBoredResponse>


}