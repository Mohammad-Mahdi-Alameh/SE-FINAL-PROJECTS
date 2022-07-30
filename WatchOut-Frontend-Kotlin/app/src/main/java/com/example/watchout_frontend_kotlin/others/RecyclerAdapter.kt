package com.example.watchout_frontend_kotlin.others

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.watchout_frontend_kotlin.R
import com.example.watchout_frontend_kotlin.api.ApiMainHeadersProvider
import com.example.watchout_frontend_kotlin.api.RestApiService
import com.google.gson.Gson
import org.json.JSONArray

class RecyclerAdapter(c: Context) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    var context = c
    var sharedPref = PreferenceManager.getDefaultSharedPreferences(context)

    var totalReports = sharedPref.getString("total_reports", "")

    override fun getItemCount(): Int {

        return totalReports?.toInt() ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        var jwtToken = sharedPref.getString("token", "")
        var id = sharedPref.getString("user_id", "")?.toInt()
        val apiService = RestApiService()
        val authenticatedHeaders =
            jwtToken?.let { ApiMainHeadersProvider.getAuthenticatedHeaders(it) }
        if (authenticatedHeaders != null) {
            if (id != null) {
                apiService.getAllInfras(authenticatedHeaders, id) {
                    if (it?.size != null) {
                        Log.i("All Infras", Gson().toJson(it))
                        val array = JSONArray(Gson().toJson(it))
                        val latitude = mutableListOf<Double>()
                        val longitude = mutableListOf<Double>()
                        val type = mutableListOf<Int>()
                        val date = mutableListOf<String>()
                        (0 until array.length()).forEach {
                            var infra = array.getJSONObject(it)
                            latitude.add(infra["latitude"] as Double)
                            longitude.add(infra["longitude"] as Double)
                            type.add(getLogoType(infra["type"] as String))
                            date.add(infra["created_at"] as String)
                        }
                        holder.itemLatitude.text = "Lat : " + latitude[position].toString()
                        holder.itemLongitude.text = "Long : " + longitude[position].toString()
                        holder.itemDate.text = "Date Created : " + date[position]
                        holder.itemType.setImageResource(type[position])


                    } else {
                        Log.i("Error", "Error in loading history")

                    }
                }
            }
        }

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