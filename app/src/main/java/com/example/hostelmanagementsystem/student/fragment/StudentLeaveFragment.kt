package com.example.hostelmanagementsystem.student.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.hostelmanagementsystem.R
import com.example.hostelmanagementsystem.student.model.LeaveModel
import com.example.hostelmanagementsystem.databinding.FragmentStudentLeaveBinding
import com.example.hostelmanagementsystem.extensions.showSnackBar
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*


class StudentLeaveFragment : Fragment() {
    private var _binding: FragmentStudentLeaveBinding? = null
    private val binding get() = _binding!!
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val leaveRef: CollectionReference = db.collection("Leave")
    private lateinit var status: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStudentLeaveBinding.inflate(inflater, container, false)
        status = "pending"

        binding.startdateLeave.setOnClickListener {
            val dpd =
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val s = monthOfYear + 1
                    val a = "$dayOfMonth/$s/$year"
                    binding.startdateLeave.setText("" + a)
                }
//            binding.startdateLeave.isEnabled=false
            val c = Calendar.getInstance()
            val mYear: Int = c.get(Calendar.YEAR)
            val mMonth: Int = c.get(Calendar.MONTH)
            val mDay: Int = c.get(Calendar.DAY_OF_MONTH)
            val serverFormat = SimpleDateFormat("DD:MM:yy", Locale.getDefault())
//            serverFormat.format(c)
            val d = DatePickerDialog(requireContext(), dpd, mYear, mMonth, mDay)
            d.datePicker.minDate = Date().time

            d.show()
        }
        binding.enddateLeave.setOnClickListener {
            val dpd =
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val s = monthOfYear + 1
                    val a = "$dayOfMonth/$s/$year"
                    binding.enddateLeave.setText("" + a)
                }
//            binding.startdateLeave.isEnabled=false
            val c = Calendar.getInstance()
            val Year: Int = c.get(Calendar.YEAR)
            val Month: Int = c.get(Calendar.MONTH)
            val Day: Int = c.get(Calendar.DAY_OF_MONTH)
            val serverFormat = SimpleDateFormat("DD:MM:yy", Locale.getDefault())
//            serverFormat.format(c)
            val d = DatePickerDialog(requireContext(), dpd, Year, Month, Day)
            d.datePicker.minDate = Date().time

            d.show()
        }
        binding.submitStudentLeave.setOnClickListener {
            submitLeave(binding.root)
        }
        return binding.root
    }

    private fun submitLeave(view: View) {
        val startDate: String = binding.startdateLeave.text.toString()
        val endDate: String = binding.enddateLeave.text.toString()
        val reason: String = binding.reasonStudentLeave.text.toString()
//        if(reason.isEmpty()){
//            binding.reasonStudentLeave.error = "Enter a reason"
//            return
//        }
//        if(startDate.isEmpty()){
//            binding.startdateStudentLeave.error = "Enter the start date"
//            return
//        }
//        if(endDate.isEmpty()){
//            binding.enddateLeave.error= "Enter the end date"
//            return
//        }
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null && startDate.isNotEmpty() && endDate.isNotEmpty() && reason.isNotEmpty()) {
            FirebaseFirestore.getInstance().collection("User").document(user.uid).get()
                .addOnCompleteListener(OnCompleteListener {
                    val result = it.result
                    if(result.exists() && result != null){
                        val name = result.getString("name").toString()
                        val sid =  result.getString("sid").toString()
                        val roomNo = result.getString("roomNo").toString()
                        val leaveModel = LeaveModel(startDate, endDate, reason, status, name, sid, roomNo)
                        leaveRef.add(leaveModel)
                            .addOnSuccessListener {
                    showSnackBar(requireActivity(), "Your leave application has been sent to the warden",binding.fakeAnchorLayout)
                    view.findNavController().navigate(R.id.studentDashboard)
                            }
                            .addOnFailureListener {
                                showSnackBar(requireActivity(), "Internal error occured",binding.fakeAnchorLayout)
                                view.findNavController().navigate(R.id.studentDashboard)
                            }
                    }
                })

        }
        else{
            showSnackBar(requireActivity(),"Please fill all the fields",binding.fakeAnchorLayout)
        }




    }
}