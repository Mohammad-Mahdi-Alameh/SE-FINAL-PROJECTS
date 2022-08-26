package com.example.watchout_frontend_kotlin.adapters

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
import com.example.watchout_frontend_kotlin.models.GetInfrasResponse
import com.google.gson.Gson
import org.json.JSONArray

class RequestsRecyclerAdapter(c: Context) : RecyclerView.Adapter<RequestsRecyclerAdapter.ViewHolder>() {
    private var infraList = mutableListOf<GetInfrasResponse>()

    var context = c

    private var sharedPref = PreferenceManager.getDefaultSharedPreferences(context)

    var totalFalseInfras = sharedPref.getString("total_false_infras", "")

    override fun getItemCount(): Int {
        return totalFalseInfras?.toInt() ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.request_view, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var jwtToken = sharedPref.getString("token", "")
        val userId = sharedPref.getString("user_id", "")?.toInt()
        val apiService = RestApiService()
        val authenticatedHeaders =
            jwtToken?.let { ApiMainHeadersProvider.getAuthenticatedHeaders(it) }
        if (authenticatedHeaders != null) {
            if (userId != null) {
                apiService.getAllInfras(authenticatedHeaders, 0) { list ->
                    if (list?.size != null) {
                        Log.i("All Infras", Gson().toJson(list))
                        val array = JSONArray(Gson().toJson(list))
                        val typeMarker = mutableListOf<Int>()
                        val status = mutableListOf<Int>()
                        (0 until array.length()).forEach {
                            var infra = array.getJSONObject(it)
                              typeMarker.add(getLogoType(infra["type"] as String))
                              status.add(getStatus(infra["pending"] as Int,infra["accepted"] as Int
                                  ,infra["rejected"] as Int))

                            val fetchedInfra = GetInfrasResponse(
                                id = infra["id"] as Int?,
                                latitude = infra["latitude"] as Double,
                                longitude = infra["longitude"] as Double,
                                type = infra["type"] as String,
                                date = infra["created_at"] as String ,
                                pending = infra["pending"] as Int,
                                accepted = infra["accepted"] as Int,
                                rejected = infra["rejected"] as Int,
                                userId = infra["user_id"] as Int,
                                isFixed = infra["is_fixed"] as Int,
                                falseInfra = infra["false_infra"] as Int,
                            )

                            infraList.add(fetchedInfra)

                        }
                        holder.itemLatitude.text = "Lat : " + infraList[position].latitude.toString()
                        holder.itemLongitude.text = "Long : " + infraList[position].longitude.toString()
                        holder.itemDate.text = infraList[position].date?.split(".")?.get(0) ?: ""
                        holder.itemType.setImageResource(typeMarker[position])
                        holder.itemStatus.setImageResource(status[position])


                    } else {
                        Log.i("Error", "Error in loading history")

                    }
                }
            }
        }

    }

    private fun getLogoType(type: String): Int {
        if (type != null) {
            when (type) {
                "Hole" -> {
                    return R.drawable.ic_hole_icon

                }
                "Blockage" -> {
                    return R.drawable.ic_blockage_icon
                }
                "Turn" -> {
                    return R.drawable.turn_icon
                }
                "Bump" -> {
                    return R.drawable.ic_bump_icon

                }
            }


        }
            return 0
    }
    private fun getStatus(pending : Int ,accepted :Int, rejected : Int): Int {
        return if (pending == 1) {
            R.drawable.ic_pending
        } else if(rejected == 1){
            R.drawable.ic_rejected
        }else{
            R.drawable.ic_accepted
        }


        return 0
    }



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemType: ImageView
        var itemStatus: ImageView
        var itemLatitude: TextView
        var itemLongitude: TextView
        var itemDate: TextView

        init {
            itemType = itemView.findViewById(R.id.image)
            itemStatus = itemView.findViewById(R.id.status)
            itemLatitude = itemView.findViewById(R.id.lat)
            itemLongitude = itemView.findViewById(R.id.lng)
            itemDate = itemView.findViewById(R.id.date)

        }

    }


}