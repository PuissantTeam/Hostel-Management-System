package com.example.hostelmanagementsystem.student.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hostelmanagementsystem.R
import com.example.hostelmanagementsystem.databinding.FragmentStudentDashboardBinding
import com.example.hostelmanagementsystem.databinding.FragmentStudentProfileBinding

class StudentProfileFragment : Fragment() {

    private lateinit var studentProfileBinding: FragmentStudentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        studentProfileBinding = FragmentStudentProfileBinding.inflate(layoutInflater)
        val rootView = studentProfileBinding.root

        return rootView
    }
}
