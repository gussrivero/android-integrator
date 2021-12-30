package com.example.android_integrator.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.android_integrator.R
import com.example.android_integrator.databinding.ActivityInitialBinding

class InitialActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInitialBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial)
        binding = ActivityInitialBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClickListeners()
    }

    fun setOnClickListeners (){
        binding.btnstart.setOnClickListener {
            if (validateInputs(binding.ETNumParticipants.text.toString())) {
                val participants =0
                Toast.makeText(this,"At least one participant to continue",Toast.LENGTH_LONG).show()
                //goActivities(participants)
            }else{
                val participants =binding.ETNumParticipants.text.toString().toInt()
                if (participants>0) {
                    goActivities(participants)
                }
            }
        }

        binding.TVTermsAndConditions.setOnClickListener {
               val intent = Intent(this, TermsAndConditionsActivity::class.java)
             startActivity(intent)
        }

    }
    fun validateInputs (inputParticipants : String):Boolean{
        return inputParticipants == "" || inputParticipants.toInt() == 0
    }
    fun goActivities(participants : Int){
        val intent = Intent(this, ActivitiesActivity::class.java)
        intent.putExtra("participants", participants)
        startActivity(intent)
    }
}