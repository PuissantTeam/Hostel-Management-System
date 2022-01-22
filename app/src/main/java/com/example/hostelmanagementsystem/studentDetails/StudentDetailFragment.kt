package com.example.hostelmanagementsystem.studentDetails

import android.content.DialogInterface
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hostelmanagementsystem.R
import com.example.hostelmanagementsystem.common.data.Prefs
import com.example.hostelmanagementsystem.databinding.FragmentStudentDetailBinding
import com.example.hostelmanagementsystem.onboarding.OnBoardingActivity
import com.example.hostelmanagementsystem.student.adapter.StudentAttendanceAdapter
import com.example.hostelmanagementsystem.student.model.Student
import com.example.hostelmanagementsystem.student.model.StudentAttendanceModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class StudentDetailFragment : Fragment() {
    private lateinit var binding: FragmentStudentDetailBinding
    private val reference = FirebaseFirestore.getInstance().collection("User")
    private lateinit var adapter: StudentAttendanceAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentDetailBinding.inflate(layoutInflater)
        val view = binding.root
        val bundle = arguments
        val id = bundle!!.getString("id")
        if (id != null) {
            reference.document(id).get()
                .addOnSuccessListener(OnSuccessListener {
                    if (it.exists()) {
                        binding.studentDetailName.text = it.getString("name")
                        """Room no: ${it.getString("roomNo")}""".also {
                            binding.studentDetailRoomNo.text = it
                        }
                        ("SID: " + it.getString("sid")).also { binding.studentDetailSid.text = it }
                        """${it.getString("district")}, ${it.getString("state")}""".also {
                            binding.studentDetailPlace.text = it
                        }
                        setUpAttendance(id)
                    }
                })
        }
        binding.editStudent.setOnClickListener(View.OnClickListener {
            val bundle = bundleOf("id" to id)
            view.findNavController()
                .navigate(R.id.action_studentDetailFragment_to_updateDetailFragment, bundle)
        })
        binding.deleteStudent.setOnClickListener(View.OnClickListener {
            val builder = AlertDialog.Builder(view.context)
            builder.setMessage("Are you sure you want to unallocate the student?")
                .setPositiveButton(
                    "Yes"
                ) { dialogInterface: DialogInterface?, i: Int ->
                    if (id != null) {
                        reference.document(id).delete()
                            .addOnSuccessListener(
                                OnSuccessListener {
                                    view.findNavController()
                                        .navigate(R.id.action_studentDetailFragment_to_studentListFragment)
                                }
                            )
                    }
                }
                .setNegativeButton(
                    "No"
                ) { dialogInterface: DialogInterface, i: Int -> dialogInterface.cancel() }
            val alertDialog = builder.create()
            alertDialog.show()
        })
        return view
    }

    private fun setUpAttendance(id: String) {
        val query =
            reference.document(id).collection("Attendance")
                .orderBy("date", Query.Direction.DESCENDING)
        val options = FirestoreRecyclerOptions.Builder<StudentAttendanceModel>()
            .setQuery(query, StudentAttendanceModel::class.java).build()

        adapter = StudentAttendanceAdapter(options)
        binding.recyclerViewAttendance.setHasFixedSize(true)
        binding.recyclerViewAttendance.layoutManager =
            LinearLayoutManager(binding.recyclerViewAttendance.context)
        binding.recyclerViewAttendance.adapter = adapter
        adapter.startListening()
    }
//
//    override fun onStart() {
//        super.onStart()
//        adapter.startListening()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        adapter.stopListening()
//    }
}