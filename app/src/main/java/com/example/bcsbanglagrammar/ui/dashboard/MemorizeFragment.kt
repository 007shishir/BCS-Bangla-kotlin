package com.example.bcsbanglagrammar.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.bcsbanglagrammar.R


class MemorizeFragment : Fragment() {

    private lateinit var memorizedViewModel: MemorizeViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,

        ): View? {
        memorizedViewModel =
            ViewModelProvider(this).get(MemorizeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_memorize, container, false)
//        val textView: TextView = root.findViewById(R.id.text_dashboard)
        memorizedViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
        })
        val mBtn_100 = root.findViewById<Button>(R.id.mBtn100)
        val mBtn_101 = root.findViewById<Button>(R.id.mBtn101)
        val mBtn_102 = root.findViewById<Button>(R.id.mBtn102)


        mBtn_100.setOnClickListener(clicklistener)
        mBtn_101.setOnClickListener(clicklistener)
        mBtn_102.setOnClickListener(clicklistener)
        return root
    }

    val clicklistener = View.OnClickListener { v: View? ->
        when(v?.id){
            R.id.mBtn100 -> go_to_recView("memOne", v)
            R.id.mBtn101 -> go_to_recView("memTwo", v)
            R.id.mBtn102 -> go_to_recView("memThree", v)
        }
    }

    private fun go_to_recView(str: String, v: View) {
        val child_name: Bundle
        child_name = Bundle()
        child_name.putString("child_name", str)
        Navigation.findNavController(v)
            .navigate(R.id.action_navigation_dashboard_to_firebaseRecView, child_name)
    }
}