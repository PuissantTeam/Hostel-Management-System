package com.example.hostelmanagementsystem.student.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hostelmanagementsystem.data.Prefs
import com.example.hostelmanagementsystem.databinding.FragmentStudentAttendanceBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat

import java.util.*


class StudentAttendanceFragment : Fragment() {

    private lateinit var studentAttendanceBinding: FragmentStudentAttendanceBinding
    private lateinit var prefs: Prefs
    private val cal = Calendar.getInstance()
    private var currentDate: Int = cal.get(Calendar.DATE)
    private lateinit var lastDate: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        studentAttendanceBinding = FragmentStudentAttendanceBinding.inflate(layoutInflater)

        studentAttendanceBinding.studentName.isSelected = true
        val rootView = studentAttendanceBinding.root

        prefs = Prefs(requireContext())

        if (currentDate != prefs.lastVisitedDateTime) {
            prefs.lastVisitedDateTime = currentDate
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                Firebase.firestore.collection("User").document(user.uid).get()
                    .addOnCompleteListener { task ->
                        val doc = task.result
                        if (doc != null && doc.exists()) {
                            val uid = doc.getString("uid").toString()
                            addAttendance(uid)
                        }
                    }
            }

        }
        val a = prefs.lastVisitedDateTime

        val calendar = Calendar.getInstance().time

        val dateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())

        studentAttendanceBinding.todaysDate.text =
            dateFormat.format(calendar).toString().replace("-", " ")
        studentAttendanceBinding.todaysDay.text =
            SimpleDateFormat("EEEE", Locale.ENGLISH).format(calendar)
        return rootView
    }


    private fun addAttendance(uid: String) {

    }
}
