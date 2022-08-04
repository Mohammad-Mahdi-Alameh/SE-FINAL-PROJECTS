package com.example.watchout_frontend_kotlin.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.watchout_frontend_kotlin.R
import com.example.watchout_frontend_kotlin.others.RequestsRecyclerAdapter

class RequestsActivity : AppCompatActivity() {
    private var layoutManager : RecyclerView.LayoutManager? = null
    private var adapter : RecyclerView.Adapter<RequestsRecyclerAdapter.ViewHolder>? = null
    private lateinit var backArrow : ImageView
    private lateinit var recyclerView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)
        backArrow = findViewById(R.id.back_arrow)

        layoutManager = LinearLayoutManager(this)

        recyclerView=findViewById(R.id.recyclerView)

        recyclerView.layoutManager = layoutManager

        adapter = RequestsRecyclerAdapter(this)

        recyclerView.adapter = adapter

        backArrow.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
            finish()
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MapsActivity::class.java))
        finish()
    }

}