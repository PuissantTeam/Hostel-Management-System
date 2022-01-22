package com.example.hostelmanagementsystem.student.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelmanagementsystem.R
import com.example.hostelmanagementsystem.admin.model.AdminStudent
import com.example.hostelmanagementsystem.student.model.AnnouncementModel
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class AnnouncementFragmentAdapter(options: FirestoreRecyclerOptions<AnnouncementModel>) :
   FirestoreRecyclerAdapter<AnnouncementModel, AnnouncementFragmentAdapter.AnnouncementHolder>(
    options
    )
    {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnouncementHolder {

            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.student_announcement_recycler_view_item, parent, false)

            return AnnouncementHolder(view)
        }

        override fun onBindViewHolder(
            holder: AnnouncementHolder,
            position: Int,
            model: AnnouncementModel
        ) {

            holder.date.text = model.date
            holder.description.text = model.description
            holder.userType.text = model.userType
        }

        class AnnouncementHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var date: TextView = itemView.findViewById(R.id.announcements_date)
            var description: TextView = itemView.findViewById(R.id.announcements_title)
            var userType: TextView = itemView.findViewById(R.id.announcement_sender)
        }


    }