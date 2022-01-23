package com.example.hostelmanagementsystem.admin.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.hostelmanagementsystem.R
import com.example.hostelmanagementsystem.admin.model.AdminNotice
import com.example.hostelmanagementsystem.databinding.FragmentAdminNoticeBinding
import com.example.hostelmanagementsystem.databinding.FragmentStudentGrievanceBinding
import com.example.hostelmanagementsystem.extensions.closeKeyboard
import com.example.hostelmanagementsystem.extensions.showSnackBar
import com.example.hostelmanagementsystem.student.model.GrievanceModel
import com.example.hostelmanagementsystem.utils.hideSoftKeyboard

import com.example.hostelmanagementsystem.extensions.showSnackBar
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*


class AdminNoticeFragment : Fragment() {


    private var _binding: FragmentAdminNoticeBinding? = null
    private val binding get() = _binding!!
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val noticeRef: CollectionReference = db.collection("Notice")
    val c = Calendar.getInstance()
    val mYear: Int = c.get(Calendar.YEAR)
    val mMonth: Int = c.get(Calendar.MONTH) + 1
    val mDay: Int = c.get(Calendar.DAY_OF_MONTH)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAdminNoticeBinding.inflate(inflater, container, false)

        binding.adminNoticeDate.setOnClickListener {
            val dpd =
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val s = monthOfYear + 1
                    val a = "$dayOfMonth/$s/$year"
                    binding.adminNoticeDate.setText("" + a)
                }
            val d = DatePickerDialog(requireContext(), dpd, mYear, mMonth, mDay)
            d.datePicker.minDate = Date().time

            d.show()
        }


        binding.submitNoticeAdmin.setOnClickListener {
            closeKeyboard()
            submitNotice(binding.root)
        }
        return binding.root
    }

    private fun submitNotice(view: View) {
        val date: String = binding.adminNoticeDate.text.toString()
        val description: String = binding.adminNoticeDesc.text.toString()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null && date.isNotEmpty() && description.isNotEmpty()) {
            FirebaseFirestore.getInstance().collection("User").document(user.uid).get()
                .addOnCompleteListener(OnCompleteListener {
                    val result = it.result
                    if (result.exists() && result != null) {
                        val userType = result.getString("userType").toString()
                        val noticeModel = AdminNotice(date, description, userType)
                        noticeRef.add(noticeModel)
                            .addOnSuccessListener {
                                showSnackBar(
                                    requireActivity(),
                                    "Notice has been sent to the students",
                                    binding.fakeAnchorLayout
                                )
                                if (userType == "admin") {
                                    view.findNavController().navigate(R.id.adminDashboardFragment)
                                } else {
                                    view.findNavController().navigate(R.id.allocationFragment)
                                }
                            }
                            .addOnFailureListener {
                                showSnackBar(
                                    requireActivity(),
                                    "Internal error occured",
                                    binding.fakeAnchorLayout
                                )
                                if (userType == "admin") {
                                    view.findNavController().navigate(R.id.adminDashboardFragment)
                                } else {
                                    view.findNavController().navigate(R.id.allocationFragment)
                                }
                            }
                    }
                })

        } else {
            showSnackBar(
                requireActivity(),
                "Please fill out all the fields",
                binding.fakeAnchorLayout
            )
        }


    }

    override fun onResume() {
        super.onResume()
        binding.adminNoticeDate.setText("$mDay/$mMonth/$mYear")

    }


}