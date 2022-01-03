package com.example.android_integrator.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.android_integrator.KeyIntents
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
          if (binding.chTermAndCon.isChecked) {
              if (binding.ETNumParticipants.text.toString().isEmpty()) {
                  goActivities(0)
              } else {
                  val participants = binding.ETNumParticipants.text.toString().toInt()
                  goActivities(participants)
              }
          }else Toast.makeText(this,getString(R.string.acept_tac),Toast.LENGTH_LONG).show()
        }

        binding.TVTermsAndConditions.setOnClickListener {
               val intent = Intent(this, TermsAndConditionsActivity::class.java)
             startActivity(intent)
        }

    }

    fun goActivities(participants : Int){
        val intent = Intent(this, ActivitiesActivity::class.java)
        intent.putExtra(KeyIntents.PARTICIPANTS.name, participants)
        startActivity(intent)
    }
}