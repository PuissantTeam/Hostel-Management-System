package com.example.hostelmanagementsystem.student.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelmanagementsystem.R
import com.example.hostelmanagementsystem.student.model.StudentAttendanceModel

import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class StudentAttendanceAdapter(options: FirestoreRecyclerOptions<StudentAttendanceModel>) :
    FirestoreRecyclerAdapter<StudentAttendanceModel, StudentAttendanceAdapter.AnnouncementHolder>(
        options
    ) {

    private var announcementUrl: String = ""

    private var onClick: OnItemClicked? = null

    interface OnItemClicked {
        fun onItemClick(position: Int, announcementUrl: String)
    }

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnouncementHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_attendance_recycler_view_item, parent, false)

        return AnnouncementHolder(view)
    }

    override fun onBindViewHolder(
        holder: AnnouncementHolder,
        position: Int,
        model: StudentAttendanceModel
    ) {
        holder.attendanceDate.text = model.date
        holder.attendanceCheckIn.text = model.checkIn
        holder.attendanceCheckOut.text = model.checkOut

        holder.itemView.setOnClickListener { onClick!!.onItemClick(position, announcementUrl) }

    }

    class AnnouncementHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var attendanceDate: TextView = itemView.findViewById(R.id.attendance_date)
        var attendanceCheckIn: TextView = itemView.findViewById(R.id.attendance_check_in)
        var attendanceCheckOut: TextView = itemView.findViewById(R.id.attendance_check_out)
    }

    fun setOnClick(onClick: OnItemClicked?) {
        this.onClick = onClick
    }
}