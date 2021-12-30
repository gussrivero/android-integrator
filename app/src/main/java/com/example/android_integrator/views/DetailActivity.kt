package com.example.android_integrator.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android_integrator.KeyIntents
import com.example.android_integrator.databinding.ActivityDetailBinding
import com.example.android_integrator.models.APINotBored
import com.example.android_integrator.models.NotBoredResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding
    private val BASE_URL = "http://www.boredapi.com/api/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val type = intent.getStringExtra(KeyIntents.DETAIL.name)
        //TODO amountParticipant
        type?.let { searchActivities(it,2) }


        binding.BTNTtryAnother.setOnClickListener {
            type?.let { searchActivities(it,2) }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    fun searchActivities(type : String,amountParticipants : Int){

            CoroutineScope(Dispatchers.IO).launch{

                val call = getRetrofit().create(APINotBored::class.java).getActivities("activity?type=$type")
                val notBoredResponse : NotBoredResponse? = call.body()

                runOnUiThread{

                    notBoredResponse.let {
                        loadResponse(it)
                        if(call.isSuccessful){

                        }else{
                            //TODO NOT SUCCESSFUL
                        }
                    } ?: run {
                            //TODO NULL RESPONSE
                    }
                }


            }

    }


    fun loadResponse(notBoredResponse: NotBoredResponse?){
        binding.TVActivity.text = notBoredResponse?.activity
        binding.TVParticipants.text = notBoredResponse?.participants.toString()



        val price = when(notBoredResponse!!.price){
            0f  -> "Free"
            in 0f..0.3f -> "Low"
            in 0.3f..0.6f -> "Medium"
            else -> "High"
        }

        binding.TVPrice.text = price

    }



}