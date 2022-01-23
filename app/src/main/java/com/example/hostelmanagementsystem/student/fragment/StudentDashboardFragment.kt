package com.example.hostelmanagementsystem.student.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View

import com.example.hostelmanagementsystem.databinding.FragmentStudentDashboardBinding
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.hostelmanagementsystem.student.model.AnnouncementModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.*


class StudentDashboardFragment : Fragment() {

    private lateinit var studentDashboardBinding: FragmentStudentDashboardBinding
    private lateinit var adapter: AnnouncementFragmentAdapter
    private val reference = FirebaseFirestore.getInstance().collection("Notice")
    private val date =  Date()
    private val calendar = Calendar.getInstance()
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

        // get day of week
        calendar.time = date
        val dayOfWeek = calendar[Calendar.DAY_OF_WEEK]

        when(dayOfWeek){
            1-> {
                studentDashboardBinding.messFoodName.text = "Roti Sabzi Daal"
                Glide.with(requireContext())
                    .load("https://image.shutterstock.com/image-photo/typical-north-indian-thali-consisting-260nw-1609322191.jpg")
                    .into(studentDashboardBinding.messFoodImage)
            }
            2-> {
                studentDashboardBinding.messFoodName.text = "Roti Chole Chawal"
                Glide.with(requireContext())
                    .load("https://qph.fs.quoracdn.net/main-qimg-eaed594da5320d5d744cc8e78bc5f1a3-pjlq")
                    .into(studentDashboardBinding.messFoodImage)
            }
            3-> {
                studentDashboardBinding.messFoodName.text = "Roti Paneer Rabdi"
                Glide.with(requireContext())
                    .load("https://thumbs.dreamstime.com/z/indian-hostel-mess-food-plate-chapati-paneer-curd-salad-having-students-full-steal-173434830.jpg")
                    .into(studentDashboardBinding.messFoodImage)
            }
            4-> {
                studentDashboardBinding.messFoodName.text = "Bread Puri Aloo"
                Glide.with(requireContext())
                    .load("https://qph.fs.quoracdn.net/main-qimg-5c271014551f25a26a2476033ca870bf-c")
                    .into(studentDashboardBinding.messFoodImage)
            }
            5-> {
                studentDashboardBinding.messFoodName.text = "Roti Sabzi Daal"
                Glide.with(requireContext())
                    .load("https://image.shutterstock.com/image-photo/typical-north-indian-thali-consisting-260nw-1609322191.jpg")
                    .into(studentDashboardBinding.messFoodImage)
            }
            6-> {
                studentDashboardBinding.messFoodName.text = "Samosa Chawal Paneer"
                Glide.with(requireContext())
                    .load("https://s01.sgp1.digitaloceanspaces.com/large/865307-79310-wfrgbwbprx-1516122023.jpg")
                    .into(studentDashboardBinding.messFoodImage)
            }
            7-> {
                studentDashboardBinding.messFoodName.text = "Roti Sabzi Daal"
                Glide.with(requireContext())
                    .load("https://image.shutterstock.com/image-photo/typical-north-indian-thali-consisting-260nw-1609322191.jpg")
                    .into(studentDashboardBinding.messFoodImage)
            }

        }

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