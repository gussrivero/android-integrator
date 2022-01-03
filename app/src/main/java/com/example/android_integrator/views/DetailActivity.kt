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
    private lateinit var participants: String
    private val RANDOM = "Random"
    lateinit var type : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

         participants = intent.getStringExtra("participants").toString()
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

    fun validateRetrofitCallCases (type: String,amountParticipants: String):String{
        //participants and random
        return if (type == RANDOM&& amountParticipants!="") "activity?participants=$amountParticipants"
        //participants and type
        else if (type != RANDOM && amountParticipants !="") "activity?type=$type&participants=$amountParticipants"
        //no participants y type
        else if (type !=RANDOM && amountParticipants=="") "activity?type=$type"
        // no participants and random
        else "activity"
    }
    fun searchActivities(type : String,amountParticipants : String){

            CoroutineScope(Dispatchers.IO).launch{
                   call = getRetrofit().create(APINotBored::class.java)
                           .getActivitiesByParticipants(validateRetrofitCallCases(type,amountParticipants))

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
            0f  -> PriceType.Free
            in 0f..0.3f -> PriceType.Low
            in 0.3f..0.6f -> PriceType.Medium
            else -> PriceType.High
        }

        binding.tvPriceDetails.text = price.toString()

    }
}