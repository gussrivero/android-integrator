package com.example.android_integrator.views

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.android_integrator.enums.KeyIntents
import com.example.android_integrator.R
import com.example.android_integrator.enums.TypeActivity
import com.example.android_integrator.databinding.ActivityDetailBinding
import com.example.android_integrator.service.ApiNotBoredImp
import com.example.android_integrator.models.NotBoredResponse
import com.example.android_integrator.models.OneActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.*
import android.view.Menu

import android.view.MenuItem
import android.widget.Toast


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.TBDetailActivities)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        loading(true)
        val oneActivity = intent.getSerializableExtra(KeyIntents.ONEACTIVITY.name) as OneActivity

        supportActionBar?.title = oneActivity.type?.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }



        if (isConnectedInternet()) searchActivities(oneActivity) else finish()
        hideType(oneActivity.type)


        binding.btnTryAnother.setOnClickListener {
            loading(true)
                if (isConnectedInternet()) searchActivities(oneActivity) else finish()
        }


    }
    //hide type if call is RANDOM
    private fun hideType(type: String) {
        if (type == TypeActivity.RANDOM.name) {
            binding.tvTypeActivityDetails.visibility = View.VISIBLE
            binding.ivTypeDetails.visibility = View.VISIBLE
        }
    }
    //chek internet connection
    private fun isConnectedInternet(): Boolean {

        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val isconnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        return if (!isconnected) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show()
            false

        } else true
    }
    //validate call cases by the user´s inputs
    private suspend fun validateRetrofitCallCases(oneActivity: OneActivity): Response<NotBoredResponse> {

        return if (oneActivity.type != TypeActivity.RANDOM.name && oneActivity.amountParticipants > 0) {//participants and type case

            if (oneActivity.minPrice > 0f || oneActivity.maxPrice < 1f) {// validate if prices is set or not
                ApiNotBoredImp().getActivitiesByParticipantsAndTypeWithPrice(//participants types and price case
                    oneActivity.type, oneActivity.amountParticipants,
                    oneActivity.minPrice, oneActivity.maxPrice
                )
            } else {
                ApiNotBoredImp().getActivitiesByParticipantsAndType(
                    oneActivity.type,
                    oneActivity.amountParticipants
                )
            }

        } else if (oneActivity.type == TypeActivity.RANDOM.name && oneActivity.amountParticipants > 0) {//participants and random case
            ApiNotBoredImp().getActivitiesByParticipants(oneActivity.amountParticipants)
        } else if (oneActivity.type != TypeActivity.RANDOM.name && oneActivity.amountParticipants == 0) {//no participants y type case
            if (oneActivity.minPrice > 0f || oneActivity.maxPrice < 1f) {
                ApiNotBoredImp().getActivitiesByTypeWithPrice(
                    oneActivity.type,
                    oneActivity.minPrice, oneActivity.maxPrice
                )
            } else {
                ApiNotBoredImp().getActivitiesByType(oneActivity.type)
            }

        } else ApiNotBoredImp().getRandom()        // no participants and random
    }


    private fun searchActivities(oneActivity: OneActivity) {

        CoroutineScope(Dispatchers.IO).launch {

            val call = validateRetrofitCallCases(oneActivity)
            val notBoredResponse: NotBoredResponse? = call.body()

            runOnUiThread {
                notBoredResponse.let {
                    if (call.isSuccessful) {
                        if (!call.body()?.activity.isNullOrEmpty()) {
                            loadResponse(it)
                        } else {
                            notResponse()
                            loading(false)
                        }
                    } else {
                        notResponse()
                        loading(false)
                    }
                }
            }
        }

    }
    // show null message response
    fun notResponse() {
        binding.tvCategoryTitleDetail.text = getString(R.string.text_notresponse)
        binding.CLDetail.visibility = View.INVISIBLE
    }

    //fill the views with response
    fun loadResponse(notBoredResponse: NotBoredResponse?) {

        binding.CLDetail.visibility = View.VISIBLE

        binding.tvCategoryTitleDetail.text = notBoredResponse?.activity
        binding.tvParticupantsDetails.text = notBoredResponse?.participants.toString()
        binding.tvTypeActivityDetails.text = notBoredResponse?.type
        loading(false)

        val price = when (notBoredResponse!!.price) {
            0f -> getString(R.string.cost_free)
            in 0f..0.3f -> getString(R.string.cost_low)
            in 0.3f..0.6f -> getString(R.string.cost_medium)
            in 0.6f..1f -> getString(R.string.cost_high)
            else -> getString(R.string.cost_free)
        }

        binding.tvPriceDetails.text = price

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }

    //show and hide progress bar
    fun loading(result: Boolean) {
        if (result)
            binding.pbLoadingData.visibility = View.VISIBLE
        else
            binding.pbLoadingData.visibility = View.GONE
    }

}