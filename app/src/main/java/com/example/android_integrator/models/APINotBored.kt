package com.example.android_integrator.models

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APINotBored {
    @GET("activity")
    suspend fun getRandom():Response<NotBoredResponse>
    @GET("activity")
    suspend fun getActivitiesByParticipantsAndType(@Query("type") type:String, @Query("participants")participants:Int): Response<NotBoredResponse>
    @GET("activity")
    suspend fun getActivitiesByType(@Query("type")type:String): Response<NotBoredResponse>
    @GET("activity")
    suspend fun getActivitiesByParticipants(@Query("participants")participants: Int): Response<NotBoredResponse>

    @GET("activity")
    suspend fun getActivitiesByParticipantsAndTypeWithPrice(@Query("type") type:String, @Query("participants")participants:Int,
                                                            @Query("minprice")minPrice: Float,@Query("maxprice")maxPrice: Float): Response<NotBoredResponse>

    @GET("activity")
    suspend fun getActivitiesByTypeWithPrice(@Query("type")type:String,
                                             @Query("minprice")minPrice: Float,@Query("maxprice")maxPrice: Float): Response<NotBoredResponse>

}