package com.example.watchout_frontend_kotlin.others

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.watchout_frontend_kotlin.R

class RecyclerAdapter() : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {


    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemType: ImageView
        var itemLatitude: TextView
        var itemLongitude: TextView
        var itemDate: TextView

        init {
            itemType = itemView.findViewById(R.id.image)
            itemLatitude = itemView.findViewById(R.id.latitude)
            itemLongitude = itemView.findViewById(R.id.longitude)
            itemDate = itemView.findViewById(R.id.date)
        }
    }
}