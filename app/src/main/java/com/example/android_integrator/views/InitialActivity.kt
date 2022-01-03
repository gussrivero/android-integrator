package com.example.android_integrator.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.android_integrator.R
import com.example.android_integrator.databinding.ActivityInitialBinding

class InitialActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInitialBinding
    val nPARTICIPANTS= "participants"
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
              val inputParticipants =binding.ETNumParticipants.text.toString().replace(" ","")
              if (inputParticipants== "") {
                  goActivities("")
              } else {
                  val participants = binding.ETNumParticipants.text.toString().replace(" ","").toInt()
                  if (participants > 0) goActivities(participants.toString())
                  else Toast.makeText(this,getString(R.string.at_least_one_participant),Toast.LENGTH_LONG).show()
              }
          }else Toast.makeText(this,getString(R.string.acept_tac),Toast.LENGTH_LONG).show()
        }

        binding.TVTermsAndConditions.setOnClickListener {
               val intent = Intent(this, TermsAndConditionsActivity::class.java)
             startActivity(intent)
        }

    }

    fun goActivities(participants : String){
        val intent = Intent(this, ActivitiesActivity::class.java)
        intent.putExtra(nPARTICIPANTS, participants.toString())
        startActivity(intent)
    }
}