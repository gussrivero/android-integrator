package com.example.android_integrator.adapter


import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.android_integrator.enums.KeyIntents
import com.example.android_integrator.databinding.ItemActivitiesBinding
import com.example.android_integrator.models.OneActivity
import com.example.android_integrator.views.DetailActivity

class ActivitiesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemActivitiesBinding.bind(view)


    fun bind(name: String, context: Context, oneActivity: OneActivity) {

        binding.TVName.text = name
        binding.root.setOnClickListener {

            val intent = Intent(context, DetailActivity::class.java).apply {
                oneActivity.type = name
                putExtra(KeyIntents.ONEACTIVITY.name, oneActivity)
            }
            context.startActivity(intent)

        }

    }

}