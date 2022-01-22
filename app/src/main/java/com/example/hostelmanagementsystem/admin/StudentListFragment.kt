package com.example.hostelmanagementsystem.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelmanagementsystem.data.AdminStudent
import com.example.hostelmanagementsystem.R
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore


class StudentListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentFragmentAdapter
    private lateinit var progressBar: ProgressBar
    private val reference = FirebaseFirestore.getInstance().collection("User")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_student_admin, container, false)


        recyclerView = rootView.findViewById(R.id.student_admin_recycler)
        progressBar = rootView.findViewById(R.id.progress_bar_student_admin)

        recyclerView.layoutManager = LinearLayoutManager(context)

        val query = reference.whereEqualTo("userType", "student")
        val options = FirestoreRecyclerOptions.Builder<AdminStudent>()
            .setQuery(query, AdminStudent::class.java).build()

        adapter = StudentFragmentAdapter(options)


        // Setting the Adapter with the recyclerview
        recyclerView.adapter = adapter

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
