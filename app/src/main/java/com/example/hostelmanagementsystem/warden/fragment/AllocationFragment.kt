package com.example.hostelmanagementsystem.warden.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hostelmanagementsystem.common.model.Register
import com.example.hostelmanagementsystem.R
import com.example.hostelmanagementsystem.databinding.FragmentAllocationBinding
import com.example.hostelmanagementsystem.extensions.closeKeyboard
import com.example.hostelmanagementsystem.extensions.showSnackBar
import com.example.hostelmanagementsystem.warden.adapter.AllocationAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore

class AllocationFragment : Fragment(R.layout.fragment_allocation) {
    private lateinit var binding: FragmentAllocationBinding
    private val ref = FirebaseFirestore.getInstance()
    private lateinit var adapter : AllocationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllocationBinding.inflate(layoutInflater)
        val view = binding.root
        roomCount(view)
        val query = ref.collection("Register").whereEqualTo("status", "pending")
        val options = FirestoreRecyclerOptions.Builder<Register>().setQuery(query, Register::class.java).build()
        adapter = AllocationAdapter(options)
        binding.recyclerViewAllocation.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewAllocation.adapter = adapter

        adapter.setOnItemClickListener(object : AllocationAdapter.OnItemClickListener {
            override fun onItemClick(documentSnapshot: String) {
                val bundle = bundleOf("id" to documentSnapshot)
                closeKeyboard()
                roomCount(view)
            }
        })

        return view
    }

    fun roomCount(view : View){
        ref.collection("Room").whereEqualTo("status", "empty").get()
            .addOnCompleteListener(OnCompleteListener {
                if(it.isSuccessful) {
                    val result = it.result
                    val count = result.count()
                    binding.textViewRoomAvailable.text = count.toString()
                }
            }).addOnFailureListener {
                showSnackBar(requireActivity(),it.toString(), view)
            }
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