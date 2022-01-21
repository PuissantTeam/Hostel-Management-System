package com.example.hostelmanagementsystem.student.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hostelmanagementsystem.R
import com.example.hostelmanagementsystem.databinding.FragmentStudentAttendanceBinding

class StudentAttendanceFragment : Fragment() {

    private lateinit var studentAttendanceBinding: FragmentStudentAttendanceBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        studentAttendanceBinding = FragmentStudentAttendanceBinding.inflate(layoutInflater)

        val rootView = studentAttendanceBinding.root
        return rootView
    }

}