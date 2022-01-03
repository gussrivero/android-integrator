package com.example.android_integrator.models

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiNotBoredImp : APINotBored {
    private val BASE_URL = "http://www.boredapi.com/api/"

    private fun getRetrofit(): APINotBored {
        val retrofit =Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            return retrofit.create(APINotBored::class.java)

    }
    override suspend fun getActivitiesByParticipants(participants:Int): Response<NotBoredResponse> {
        return  getRetrofit().getActivitiesByParticipants(participants)
    }
    override suspend fun getActivitiesByType(type: String): Response<NotBoredResponse> {
        return  getRetrofit().getActivitiesByType(type)
    }
    override suspend fun getActivitiesByParticipantsAndType(
        type: String,
        participants: Int
    ): Response<NotBoredResponse> {
      return  getRetrofit().getActivitiesByParticipantsAndType(type,participants)
    }

    override suspend fun getRandom(): Response<NotBoredResponse> {
     return getRetrofit().getRandom()
    }
}