package com.example.hostelmanagementsystem.student.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.hostelmanagementsystem.R
import com.example.hostelmanagementsystem.databinding.FragmentStudentGrievanceBinding
import com.example.hostelmanagementsystem.extensions.showSnackBar
import com.example.hostelmanagementsystem.student.model.GrievanceModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


class StudentGrievanceFragment : Fragment() {
    private var _binding: FragmentStudentGrievanceBinding? = null
    private val binding get() = _binding!!
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val grievRef: CollectionReference = db.collection("Grievance")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStudentGrievanceBinding.inflate(inflater, container, false)



        binding.submitStudentGrievance.setOnClickListener {
            submitGrievance(binding.root)
        }
        return binding.root
    }

    private fun submitGrievance(view: View) {
        val title: String = binding.titleStudentGrievance.text.toString()
        val description: String = binding.descStudentGrievance.text.toString()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null && title.isNotEmpty() && description.isNotEmpty()) {
            FirebaseFirestore.getInstance().collection("User").document(user.uid).get()
                .addOnCompleteListener(OnCompleteListener {
                    val result = it.result
                    if(result.exists() && result != null){
                        val name = result.getString("name").toString()
                        val sid =  result.getString("sid").toString()
                        val uid = result.getString("uid").toString()
                        val grievanceModel = GrievanceModel(title, description,  name, sid, uid, "0")
                        grievRef.add(grievanceModel)
                            .addOnSuccessListener {
                                showSnackBar(requireActivity(), "Your Grievance has been sent to the warden",binding.fakeAnchorLayout)
                                view.findNavController().navigate(R.id.studentDashboard)
                            }
                            .addOnFailureListener {
                                showSnackBar(requireActivity(), "Internal error occured",binding.fakeAnchorLayout)
                                view.findNavController().navigate(R.id.studentDashboard)
                            }
                    }
                })

        }
        else{
            showSnackBar(requireActivity(),"Please fill out all the fields",binding.fakeAnchorLayout)
        }



    }
}