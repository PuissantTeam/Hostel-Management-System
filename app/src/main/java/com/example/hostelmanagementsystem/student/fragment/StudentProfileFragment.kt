package com.example.hostelmanagementsystem.student.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hostelmanagementsystem.databinding.FragmentStudentProfileBinding
import com.example.hostelmanagementsystem.extensions.showSnackBar
import com.example.hostelmanagementsystem.extensions.timer
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class StudentProfileFragment : Fragment() {


    private lateinit var studentProfileBinding: FragmentStudentProfileBinding

    private lateinit var anchorView: BottomNavigationView
    val TAG: String = StudentProfileFragment::class.java.name

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        studentProfileBinding = FragmentStudentProfileBinding.inflate(layoutInflater)
        val rootView = studentProfileBinding.root


        val user = FirebaseAuth.getInstance().currentUser
        timer(studentProfileBinding.progressBar, 400)
        Firebase.firestore.collection("User").document(user!!.uid).get().addOnCompleteListener {
            val doc = it.result
            if (doc != null) {
                studentProfileBinding.profileStudentFullName.text = doc.getString("name").toString()
                studentProfileBinding.profileStudentMail.text = doc.getString("email").toString()
                studentProfileBinding.profileRoomAllocated.text = doc.getString("roomNo").toString()
                studentProfileBinding.profileStudentId.text = doc.getString("sid").toString()
                studentProfileBinding.profileUsername.text = doc.getString("name").toString()
            }
        }.addOnFailureListener {
            Log.d(TAG, it.message.toString())
            showSnackBar(requireActivity(), it.message, anchorView)
        }

        return rootView
    }
}
