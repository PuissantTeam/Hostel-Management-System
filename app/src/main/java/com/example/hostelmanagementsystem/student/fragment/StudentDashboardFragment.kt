package com.example.hostelmanagementsystem.student.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View

import com.example.hostelmanagementsystem.databinding.FragmentStudentDashboardBinding
import android.content.Intent
import android.view.ViewGroup


class StudentDashboardFragment : Fragment() {

    private lateinit var studentDashboardBinding: FragmentStudentDashboardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        studentDashboardBinding = FragmentStudentDashboardBinding.inflate(layoutInflater)
        val rootView = studentDashboardBinding.root

        return rootView
    }
}