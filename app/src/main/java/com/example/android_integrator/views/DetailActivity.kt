package com.example.android_integrator.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.android_integrator.KeyIntents
import com.example.android_integrator.R
import com.example.android_integrator.TypeActivity
import com.example.android_integrator.databinding.ActivityDetailBinding
import com.example.android_integrator.models.APINotBored
import com.example.android_integrator.models.ApiNotBoredImp
import com.example.android_integrator.models.NotBoredResponse
import com.example.android_integrator.models.OneActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.TBDetailActivities)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val oneActivity = intent.getSerializableExtra(KeyIntents.ONEACTIVITY.name) as OneActivity

        supportActionBar?.title = oneActivity.type?.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }


        oneActivity.type?.let {
            searchActivities(oneActivity)
            hideType(it)
        }

        binding.btnTryAnother.setOnClickListener {
            oneActivity.type?.let { searchActivities(oneActivity) }
        }


    }

    private fun hideType(type: String) {
        if (type == TypeActivity.RANDOM.name) {
            binding.tvTypeActivityDetails.visibility = View.VISIBLE
            binding.ivTypeDetails.visibility = View.VISIBLE
        }
    }


    suspend fun validateRetrofitCallCases (oneActivity: OneActivity): Response<NotBoredResponse> {
        System.out.println("MIN "+oneActivity.minPrice+" MAX"+oneActivity.maxPrice)
        return if (oneActivity.type != TypeActivity.RANDOM.name && oneActivity.amountParticipants > 0) {//participants and type

            if(oneActivity.minPrice > 0f || oneActivity.maxPrice < 1f){
                ApiNotBoredImp().getActivitiesByParticipantsAndTypeWithPrice(oneActivity.type , oneActivity.amountParticipants,
                oneActivity.minPrice,oneActivity.maxPrice)
            }else{
                ApiNotBoredImp().getActivitiesByParticipantsAndType(oneActivity.type , oneActivity.amountParticipants)
            }

        } else if (oneActivity.type  == TypeActivity.RANDOM.name && oneActivity.amountParticipants > 0) {//participants and random
            ApiNotBoredImp().getActivitiesByParticipants(oneActivity.amountParticipants)
        } else if (oneActivity.type  != TypeActivity.RANDOM.name && oneActivity.amountParticipants == 0) {//no participants y type
            if(oneActivity.minPrice > 0f || oneActivity.maxPrice < 1f){
                ApiNotBoredImp().getActivitiesByTypeWithPrice(oneActivity.type,
                    oneActivity.minPrice,oneActivity.maxPrice)
            }else{
                ApiNotBoredImp().getActivitiesByType(oneActivity.type)
            }


        } else ApiNotBoredImp().getRandom()        // no participants and random
    }


    fun searchActivities(oneActivity: OneActivity){


            CoroutineScope(Dispatchers.IO).launch{

                val call = validateRetrofitCallCases(oneActivity)
                val notBoredResponse : NotBoredResponse? = call.body()

                runOnUiThread{

                    notBoredResponse.let {
                        if(call.isSuccessful){
                            if(!call.body()?.activity.isNullOrEmpty()){
                                loadResponse(it)
                            }else{
                                notResponse()
                            }
                        }else{
                            notResponse()
                        }
                    }
                }
            }

    }

    fun notResponse(){
        binding.tvCategoryTitleDetail.text = getString(R.string.text_notresponse)
        binding.CLDetail.visibility = View.INVISIBLE
    }


    fun loadResponse(notBoredResponse: NotBoredResponse?){

        binding.CLDetail.visibility = View.VISIBLE

        binding.tvCategoryTitleDetail.text = notBoredResponse?.activity
        binding.tvParticupantsDetails.text = notBoredResponse?.participants.toString()
        binding.tvTypeActivityDetails.text = notBoredResponse?.type


        val price = when(notBoredResponse!!.price){
            0f  -> getString(R.string.cost_free)
            in 0f..0.3f -> getString(R.string.cost_low)
            in 0.3f..0.6f -> getString(R.string.cost_medium)
            in 0.6f..1f -> getString(R.string.cost_high)
            else -> getString(R.string.cost_free)
        }

        binding.tvPriceDetails.text = price

    }



}