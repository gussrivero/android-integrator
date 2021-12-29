package com.example.android_integrator

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ActivitiesAdapter(private val nameActivities : List<String>, val context : Context) : RecyclerView.Adapter<ActivitiesViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivitiesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ActivitiesViewHolder(layoutInflater.inflate(R.layout.item_activities, parent, false))

    }

    override fun onBindViewHolder(holder: ActivitiesViewHolder, position: Int) {
        val nameAtPosition = nameActivities[position]
        holder.bind(nameAtPosition,context)

    }

    override fun getItemCount(): Int = nameActivities.size

}