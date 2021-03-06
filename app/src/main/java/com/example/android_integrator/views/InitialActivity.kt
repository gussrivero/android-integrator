package com.example.android_integrator.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.android_integrator.enums.KeyIntents
import com.example.android_integrator.R
import com.example.android_integrator.databinding.ActivityInitialBinding
import com.example.android_integrator.models.OneActivity
import com.google.android.material.slider.RangeSlider

class InitialActivity : AppCompatActivity(), RangeSlider.OnChangeListener {
    private lateinit var binding: ActivityInitialBinding
    private var oneActivity =  OneActivity(0,"",0f,1f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial)
        binding = ActivityInitialBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClickListeners()
        binding.SBPrice.addOnChangeListener(this)


    }

    fun setOnClickListeners (){
        binding.btnstart.setOnClickListener {
          if (binding.chTermAndCon.isChecked) {
              val cant = binding.ETNumParticipants.text.toString().replace(",","").
              replace(".","").replace("-","").trim()

              if (cant.isEmpty()) {
                  goActivities(oneActivity)
              } else {
                  oneActivity.amountParticipants = cant.toInt()
                  goActivities(oneActivity)
              }
          }else Toast.makeText(this,getString(R.string.acept_tac),Toast.LENGTH_LONG).show()
        }

        binding.TVTermsAndConditions.setOnClickListener {
               val intent = Intent(this, TermsAndConditionsActivity::class.java)
             startActivity(intent)
        }

    }

    fun goActivities(oneActivity: OneActivity){
        val intent = Intent(this, ActivitiesActivity::class.java)
        intent.putExtra(KeyIntents.ONEACTIVITY.name, oneActivity)
        startActivity(intent)
    }

    override fun onValueChange(slider: RangeSlider, value: Float, fromUser: Boolean) {
        oneActivity.minPrice = slider.values[0]
        oneActivity.maxPrice = slider.values[1]
    }


}