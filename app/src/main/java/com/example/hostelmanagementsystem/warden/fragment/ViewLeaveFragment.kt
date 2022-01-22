package com.example.hostelmanagementsystem.warden.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hostelmanagementsystem.R
import com.example.hostelmanagementsystem.student.model.LeaveModel
import com.example.hostelmanagementsystem.databinding.FragmentViewLeaveBinding
import com.example.hostelmanagementsystem.warden.adapter.LeaveAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore


class ViewLeaveFragment : Fragment(R.layout.fragment_view_leave) {
    private lateinit var binding: FragmentViewLeaveBinding
    private val ref = FirebaseFirestore.getInstance()
    private lateinit var adapter : LeaveAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewLeaveBinding.inflate(layoutInflater)
        val view =binding.root

        val query = ref.collection("Leave")
        val options = FirestoreRecyclerOptions.Builder<LeaveModel>().setQuery(query, LeaveModel::class.java).build()
        adapter = LeaveAdapter(options)
        binding.recyclerViewApplication.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewApplication.adapter = adapter
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