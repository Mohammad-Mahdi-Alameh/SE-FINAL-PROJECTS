package com.example.watchout_frontend_kotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation


class HomeFragment : Fragment() ,View.OnClickListener {

    var navc : NavController ?= null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navc = Navigation.findNavController(view)
        view.findViewById<ImageView>(R.id.imageButton).setOnClickListener (this)
    }

    override fun onClick(v: View?) {
        navc?.navigate(R.id.action_homeFragment_to_reportFragment)
    }
}