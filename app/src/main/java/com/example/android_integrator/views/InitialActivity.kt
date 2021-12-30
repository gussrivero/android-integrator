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


        binding.btnstart.setOnClickListener {
                val int =binding.ETNumParticipants.text.toString().toInt()
            if (int>0) {
                val intent = Intent(this, ActivitiesActivity::class.java)
                intent.putExtra("participants", binding.ETNumParticipants.inputType)
                startActivity(intent)
            }
        }

    }
}