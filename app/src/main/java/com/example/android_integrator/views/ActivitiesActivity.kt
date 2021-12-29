package com.example.android_integrator.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_integrator.ActivitiesAdapter
import com.example.android_integrator.R
import com.example.android_integrator.databinding.ActivityActivitiesBinding


class ActivitiesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityActivitiesBinding
    private lateinit var adapter: ActivitiesAdapter
    private val nameActivities = mutableListOf<String>("education", "recreational", "social", "diy",
        "charity", "cooking", "relaxation", "music", "busywork")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityActivitiesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.TBActivities)


        loadRecyclerView()

    }


    private fun loadRecyclerView() {

        adapter = ActivitiesAdapter(nameActivities,this)
        binding.RVActivities.layoutManager = LinearLayoutManager(this)
        binding.RVActivities.adapter = adapter

    }


    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.ActionRandom -> {
            //TODO get random activity
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }
}