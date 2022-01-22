package com.example.hostelmanagementsystem.student.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View

import com.example.hostelmanagementsystem.databinding.FragmentStudentDashboardBinding
import android.content.Intent
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelmanagementsystem.admin.adapter.StudentFragmentAdapter
import com.example.hostelmanagementsystem.admin.model.AdminStudent
import com.example.hostelmanagementsystem.student.model.AnnouncementModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class StudentDashboardFragment : Fragment() {

    private lateinit var studentDashboardBinding: FragmentStudentDashboardBinding
    private lateinit var adapter: AnnouncementFragmentAdapter
    private val reference = FirebaseFirestore.getInstance().collection("Notice")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        studentDashboardBinding = FragmentStudentDashboardBinding.inflate(layoutInflater)
        val rootView = studentDashboardBinding.root
        studentDashboardBinding.announcementsRecycler.layoutManager = LinearLayoutManager(context)

        val query = reference.orderBy("date", Query.Direction.DESCENDING)
        val options = FirestoreRecyclerOptions.Builder<AnnouncementModel>()
            .setQuery(query, AnnouncementModel::class.java).build()

        adapter = AnnouncementFragmentAdapter(options)


        // Setting the Adapter with the recyclerview
        studentDashboardBinding.announcementsRecycler.adapter = adapter

        return rootView
    }
    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}