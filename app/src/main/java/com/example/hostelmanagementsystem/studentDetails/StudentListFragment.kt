package com.example.hostelmanagementsystem.studentDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelmanagementsystem.R
import com.example.hostelmanagementsystem.student.model.Student
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore


class StudentListFragment : Fragment() {
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
        val options = FirestoreRecyclerOptions.Builder<Student>()
            .setQuery(query, Student::class.java).build()

        adapter = StudentFragmentAdapter(options)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : StudentFragmentAdapter.OnItemClickListener {
            override fun onItemClick(documentSnapshot: String) {
                val bundle = bundleOf("id" to documentSnapshot)
                rootView.findNavController().navigate(R.id.action_studentListFragment_to_studentDetailFragment, bundle)
            }
        })

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
