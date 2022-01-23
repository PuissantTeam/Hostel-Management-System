package com.example.hostelmanagementsystem.admin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.hostelmanagementsystem.R
import com.example.hostelmanagementsystem.databinding.FragmentAdminDashboardBinding


class AdminDashboardFragment : Fragment() {
    private lateinit var adminDashboardBinding: FragmentAdminDashboardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        adminDashboardBinding = FragmentAdminDashboardBinding.inflate(layoutInflater)
        val rootView = adminDashboardBinding.root

        adminDashboardBinding.studentsViewCard.setOnClickListener{
            view?.findNavController()?.navigate(R.id.studentListFragment)
        }

        adminDashboardBinding.wardenViewCard.setOnClickListener{
            view?.findNavController()?.navigate(R.id.wardenListFragment)
        }

        adminDashboardBinding.sendNoticeCard.setOnClickListener{
            view?.findNavController()?.navigate(R.id.adminNoticeFragment)
        }

        adminDashboardBinding.grievancesViewCard.setOnClickListener{
            view?.findNavController()?.navigate(R.id.viewGrievanceFragment)
        }

        return rootView

    }

}