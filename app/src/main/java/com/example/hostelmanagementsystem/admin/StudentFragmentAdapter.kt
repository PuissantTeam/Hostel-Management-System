package com.example.hostelmanagementsystem.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelmanagementsystem.data.AdminStudent
import com.example.hostelmanagementsystem.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions


class StudentFragmentAdapter(options: FirestoreRecyclerOptions<AdminStudent>) :
    FirestoreRecyclerAdapter<AdminStudent, StudentFragmentAdapter.AnnouncementHolder>(
        options
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnouncementHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.admin_item_student, parent, false)

        return AnnouncementHolder(view)
    }

    override fun onBindViewHolder(
        holder: AnnouncementHolder,
        position: Int,
        model: AdminStudent
    ) {

            holder.name.text = model.name
            holder.ID.text = model.sid
            holder.email.text = model.email

    }

    class AnnouncementHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.student_item_name)
        var ID: TextView = itemView.findViewById(R.id.student_item_ID)
        var email: TextView = itemView.findViewById(R.id.student_item_email)
    }

}


