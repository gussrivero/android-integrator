package com.example.android_integrator.models

import java.io.Serializable

data class OneActivity(var amountParticipants:Int = 0, var type:String, var minPrice:Float = 0f, var maxPrice:Float = 1f) : Serializable
