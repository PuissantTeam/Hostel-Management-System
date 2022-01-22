package com.example.hostelmanagementsystem.student.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.example.hostelmanagementsystem.R
import com.example.hostelmanagementsystem.databinding.FragmentStudentDashboardBinding
import com.example.hostelmanagementsystem.databinding.FragmentStudentProfileBinding
import com.example.hostelmanagementsystem.extensions.timer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import io.reactivex.rxjava3.core.Completable.timer

class StudentProfileFragment : Fragment() {




    private lateinit var studentProfileBinding: FragmentStudentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        studentProfileBinding = FragmentStudentProfileBinding.inflate(layoutInflater)
        val rootView = studentProfileBinding.root


        val user =FirebaseAuth.getInstance().currentUser
        timer(studentProfileBinding.progressBar,800)
        Firebase.firestore.collection("User").document().get().addOnCompleteListener{
            val doc =it.result
            if(doc!=null)
            {
                studentProfileBinding.profileStudentFullName.text = doc.getString("name").toString()
                studentProfileBinding.profileStudentMail.text = doc.getString("email").toString()
                studentProfileBinding.profileRoomAllocated.text = doc.getString("roomNo").toString()
                studentProfileBinding.profileStudentId.text = doc.getString("sid").toString()
            }
        }

        return rootView
    }
}
