package com.example.hostelmanagementsystem.student.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hostelmanagementsystem.databinding.FragmentStudentAttendanceBinding
import android.R
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class StudentAttendanceFragment : Fragment() {

    private lateinit var studentAttendanceBinding: FragmentStudentAttendanceBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        studentAttendanceBinding = FragmentStudentAttendanceBinding.inflate(layoutInflater)

        studentAttendanceBinding.studentName.isSelected = true
        val rootView = studentAttendanceBinding.root
        return rootView
    }

}