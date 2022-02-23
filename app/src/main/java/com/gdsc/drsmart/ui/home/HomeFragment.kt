package com.gdsc.drsmart.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gdsc.drsmart.R
import com.gdsc.drsmart.ui.home.activities.LungActivity
import com.gdsc.drsmart.ui.home.activities.SkinActivity
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        view.skinCard.setOnClickListener {
            val i = Intent(context, SkinActivity::class.java)
            startActivity(i);
        }
        view.lungCard.setOnClickListener {
            val i = Intent(context, LungActivity::class.java)
            startActivity(i);
        }

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return view;
    }


}