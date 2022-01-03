package com.example.android_integrator.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_integrator.ActivitiesAdapter
import com.example.android_integrator.KeyIntents
import com.example.android_integrator.R
import com.example.android_integrator.TypeActivity
import com.example.android_integrator.databinding.ActivityActivitiesBinding


class ActivitiesActivity : AppCompatActivity() {

    private var participants = 0
    private lateinit var binding: ActivityActivitiesBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityActivitiesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.TBActivities)

        participants = intent.getIntExtra(KeyIntents.PARTICIPANTS.name,0)

        loadRecyclerView()
    }


    private fun loadRecyclerView() {

        val nameActivities = mutableListOf<String>("education", "recreational", "social", "diy",
            "charity", "cooking", "relaxation", "music", "busywork")
        val adapter = ActivitiesAdapter(nameActivities,this, participants)
        binding.RVActivities.layoutManager = LinearLayoutManager(this)
        binding.RVActivities.adapter = adapter

    }


    override fun onOptionsItemSelected(item: MenuItem) :Boolean {
        when (item.itemId) {
            R.id.ActionRandom -> {
                val intent = Intent(this, DetailActivity::class.java).apply {
                    putExtra(KeyIntents.PARTICIPANTS.name,participants)
                    putExtra(KeyIntents.DETAIL.name, TypeActivity.RANDOM.name)
                }
                startActivity(intent)
            }


        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.toolbar_activities,menu)
        return super.onCreateOptionsMenu(menu)
    }
}