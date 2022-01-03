package com.example.android_integrator


import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.android_integrator.databinding.ItemActivitiesBinding
import com.example.android_integrator.views.DetailActivity

class ActivitiesViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemActivitiesBinding.bind(view)


    fun bind(name : String,context : Context, participants:String){

        binding.TVName.text = name
        binding.root.setOnClickListener {

            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra(KeyIntents.DETAIL.name, name)
                putExtra("participants",participants)
            }
            context.startActivity(intent)

        }

    }

}