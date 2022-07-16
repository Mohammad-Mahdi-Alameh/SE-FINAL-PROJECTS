package com.example.watchout_frontend_kotlin.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.watchout_frontend_kotlin.R
import com.example.watchout_frontend_kotlin.api.RestApiService
import com.example.watchout_frontend_kotlin.models.ReportInfo


class ReportFragment : Fragment() , View.OnClickListener{

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.hole_btn).setOnClickListener (this)
        view.findViewById<Button>(R.id.turn_btn).setOnClickListener (this)
        view.findViewById<Button>(R.id.bump_btn).setOnClickListener (this)
        view.findViewById<Button>(R.id.blockage_btn).setOnClickListener (this)
    }

    override fun onClick(v: View?) {
        fun getType():String{
            val type = ""
            if (v != null) {
                when (v.id) {
                    R.id.hole_btn -> {
                        return type.replace("", "hole")
                    }
                    R.id.turn_btn -> {
                        return type.replace("", "turn")
                    }
                    R.id.bump_btn -> {
                        return type.replace("", "bump")
                    }
                    R.id.blockage_btn -> {
                        return type.replace("", "blockage")
                    }
                }
            }
            return  type
        }
        val apiService = RestApiService()
        val reportInfo = ReportInfo(
            latitude = "live latitude",
            longitude = "live longitude",
            type = getType()
        )

        apiService.report(reportInfo) {
            if (it?.message == "Infrastructural problem reported successfully") {
                Log.i("Report Succeeded", it.message)
            } else {
                if (it?.message == "There is no such type!") {
                    Log.i("Report failed", getType())
                }
                else {
                    Log.i("Error", "Report Failed !")
                }
            }
        }
    }

}
//temporary type of the problem and  longitude will be stored in db as "live longitude"
// and latitude will be stored in db as "live latitude" because live location isn't activated yet
