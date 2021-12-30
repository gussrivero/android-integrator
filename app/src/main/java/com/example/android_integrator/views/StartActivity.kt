package com.example.android_integrator.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.android_integrator.R
import com.example.android_integrator.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding
            override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                binding = ActivityStartBinding.inflate(layoutInflater)
                setContentView(binding.root)
        setContentView(R.layout.activity_start)

                setOnClickListeners()

            }
                 private fun setOnClickListeners() {
                    binding.btnstart.setOnClickListener {
                        Toast.makeText(this,"hola",Toast.LENGTH_LONG).show()
                      //  val intent = Intent(this, ActivitiesActivity::class.java)
                        //intent.putExtra("participants", binding.ETNumParticipants.inputType)
                      //  startActivity(intent)

                    }

    }


}