package com.example.android_integrator.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.android_integrator.KeyIntents
import com.example.android_integrator.databinding.ActivityDetailBinding
import com.example.android_integrator.models.APINotBored
import com.example.android_integrator.models.NotBoredResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding
    private val BASE_URL = "http://www.boredapi.com/api/"
    private lateinit var call : Response<NotBoredResponse>
    var participants: Int? = null
    lateinit var type : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

         participants = intent.getIntExtra("participants",1)
         type = intent.getStringExtra(KeyIntents.DETAIL.name).toString()

        binding.TBDetailActivities.title = type.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
                //TODO amountParticipant
        type?.let { searchActivities(it,participants) }

        binding.btnTryAnother.setOnClickListener {
            type?.let { searchActivities(it,participants) }
        }

        hideType(type)
    }

    private fun hideType(type: String) {
        if (type=="Random") {
            binding.tvTypeActivityDetails.visibility = View.VISIBLE
            binding.ivTypeDetails.visibility = View.VISIBLE
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    fun searchActivities(type : String,amountParticipants : Int?){

            CoroutineScope(Dispatchers.IO).launch{
                   call = if (amountParticipants != null) {
                       getRetrofit().create(APINotBored::class.java)
                           .getActivitiesByParticipants("activity?participants=$amountParticipants")
                   }else {
                       getRetrofit().create(APINotBored::class.java)
                           .getActivitiesByType("activity?type=$type")
                   }


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
        binding.tvCategoryTitleDetail.text = notBoredResponse?.activity
        binding.tvParticupantsDetails.text = notBoredResponse?.participants.toString()
        binding.tvTypeActivityDetails.text = notBoredResponse?.type


        val price = when(notBoredResponse!!.price){
            0f  -> "Free"
            in 0f..0.3f -> "Low"
            in 0.3f..0.6f -> "Medium"
            else -> "High"
        }

        binding.tvPriceDetails.text = price

    }



}