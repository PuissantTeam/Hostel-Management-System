package com.example.hostelmanagementsystem.warden.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hostelmanagementsystem.R
import com.example.hostelmanagementsystem.databinding.FragmentViewGrievanceBinding
import com.example.hostelmanagementsystem.databinding.FragmentViewLeaveBinding
import com.example.hostelmanagementsystem.student.model.GrievanceModel
import com.example.hostelmanagementsystem.student.model.LeaveModel
import com.example.hostelmanagementsystem.warden.adapter.GrievanceAdapter
import com.example.hostelmanagementsystem.warden.adapter.LeaveAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class ViewGrievanceFragment : Fragment() {
    private lateinit var binding : FragmentViewGrievanceBinding
    private val ref = FirebaseFirestore.getInstance()
    private lateinit var adapter : GrievanceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewGrievanceBinding.inflate(layoutInflater)
        val view = binding.root
        val query = ref.collection("Grievance").orderBy("status", Query.Direction.ASCENDING)
        val options = FirestoreRecyclerOptions.Builder<GrievanceModel>().setQuery(query, GrievanceModel::class.java).build()
        adapter = GrievanceAdapter(options)
        binding.recyclerViewGrievances.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewGrievances.adapter = adapter
        return view
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