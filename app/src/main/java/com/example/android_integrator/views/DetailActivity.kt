package com.example.android_integrator.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.android_integrator.KeyIntents
import com.example.android_integrator.R
import com.example.android_integrator.TypeActivity
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val participants = intent.getIntExtra(KeyIntents.PARTICIPANTS.name,0)
        val type = intent.getStringExtra(KeyIntents.DETAIL.name)

        binding.TBDetailActivities.title = type?.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }


        type?.let {
            searchActivities(it,participants)
            hideType(it)
        }

        binding.btnTryAnother.setOnClickListener {
            type?.let { searchActivities(it,participants) }
        }


    }

    private fun hideType(type: String) {
        if (type == TypeActivity.RANDOM.name) {
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


    fun validateRetrofitCallCases (type: String,amountParticipants: Int):String{

        //participants and random
        return if (type == TypeActivity.RANDOM.name && amountParticipants > 0) "activity?participants=$amountParticipants"
        //participants and type
        else if (type != TypeActivity.RANDOM.name && amountParticipants > 0) "activity?type=$type&participants=$amountParticipants"
        //no participants y type
        else if (type != TypeActivity.RANDOM.name && amountParticipants == 0) "activity?type=$type"
        // no participants and random
        else "activity"
    }

    fun searchActivities(type : String,amountParticipants : Int){


            CoroutineScope(Dispatchers.IO).launch{
                val call = getRetrofit().create(APINotBored::class.java)
                           .getActivitiesByParticipants(validateRetrofitCallCases(type,amountParticipants))

                val notBoredResponse : NotBoredResponse? = call.body()

                runOnUiThread{

                    notBoredResponse.let {
                        if(call.isSuccessful){
                            if(!call.body()?.activity.isNullOrEmpty()){
                                loadResponse(it)
                            }
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
            0f  -> getString(R.string.cost_free)
            in 0f..0.3f -> getString(R.string.cost_low)
            in 0.3f..0.6f -> getString(R.string.cost_medium)
            else -> getString(R.string.cost_high)
        }

        binding.tvPriceDetails.text = price

    }



}