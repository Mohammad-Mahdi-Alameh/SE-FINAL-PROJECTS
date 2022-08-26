package com.example.watchout_frontend_kotlin.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.watchout_frontend_kotlin.R
import com.example.watchout_frontend_kotlin.others.RecyclerAdapter

class HistoryActivity : AppCompatActivity() {
    private var layoutManager : RecyclerView.LayoutManager? = null
    private var adapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        val recyclerView : RecyclerView

        layoutManager = LinearLayoutManager(this)

        recyclerView=findViewById(R.id.recyclerView)

        recyclerView.layoutManager = layoutManager

        adapter = RecyclerAdapter(this)

        recyclerView.adapter = adapter

    }
}