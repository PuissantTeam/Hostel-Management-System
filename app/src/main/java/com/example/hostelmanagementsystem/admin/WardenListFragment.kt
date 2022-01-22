package com.example.hostelmanagementsystem.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelmanagementsystem.R
import com.example.hostelmanagementsystem.data.AdminStudent
import com.example.hostelmanagementsystem.data.AdminWarden
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore


class WardenListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WardenFragmentAdapter
    private lateinit var progressBar: ProgressBar
    private val reference = FirebaseFirestore.getInstance().collection("User")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_warden_admin, container, false)

       recyclerView = rootView.findViewById(R.id.warden_admin_recycler)
        progressBar = rootView.findViewById(R.id.progress_bar_warden_admin)

        recyclerView.layoutManager = LinearLayoutManager(context)

        val query = reference.whereEqualTo("userType", "warden")
        val options = FirestoreRecyclerOptions.Builder<AdminWarden>()
            .setQuery(query, AdminWarden::class.java).build()

        adapter = WardenFragmentAdapter(options)


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