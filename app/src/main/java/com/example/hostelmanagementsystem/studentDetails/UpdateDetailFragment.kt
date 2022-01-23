package com.example.hostelmanagementsystem.studentDetails

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.example.hostelmanagementsystem.R
import com.example.hostelmanagementsystem.databinding.FragmentUpdateDetailBinding
import com.example.hostelmanagementsystem.extensions.closeKeyboard
import com.example.hostelmanagementsystem.extensions.showSnackBar
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore

class UpdateDetailFragment : Fragment() {
    private lateinit var binding : FragmentUpdateDetailBinding
    private val reference = FirebaseFirestore.getInstance().collection("User")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateDetailBinding.inflate(layoutInflater)
        val view = binding.root
        val bundle = arguments
        val id = bundle!!.getString("id")
        if(id!= null){
            reference.document(id).get()
                .addOnSuccessListener(OnSuccessListener {
                    if(it.exists()){
                        binding.updateName.text = it.getString("name")?.toEditable()
                        binding.updateSid.text = it.getString("sid")?.toEditable()
                    }
                })
        }
        binding.updateButton.setOnClickListener(View.OnClickListener {
            closeKeyboard()
            var name = binding.updateName.text.toString().trim()
            var sid = binding.updateSid.text.toString().trim()
            if(name.isEmpty()){
                binding.updateNameLayout.error = "Enter name"
            }
            if(sid.isEmpty()){
                binding.updateSidLayout.error = "Enter sid"
            }
            if (id != null) {
                updateDetails(id, name, sid, view)
            }
        })
        return view
    }

    private fun updateDetails(id: String, name: String, sid: String, view: View) {
        val update = hashMapOf(
            "name" to name,
            "sid" to sid
        )
        reference.document(id).update(update as Map<String, Any>)
            .addOnSuccessListener(OnSuccessListener {
                showSnackBar(requireActivity(), "Update Successfully", binding.fakeAnchorLayout)
                val bundle = bundleOf("id" to id)
                view.findNavController().navigate(R.id.action_updateDetailFragment_to_studentDetailFragment, bundle)
            })
    }

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

}