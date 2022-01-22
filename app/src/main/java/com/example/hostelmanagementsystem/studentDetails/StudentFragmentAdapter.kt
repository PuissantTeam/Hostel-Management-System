package com.example.hostelmanagementsystem.studentDetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelmanagementsystem.R
import com.example.hostelmanagementsystem.student.model.Student
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions


class StudentFragmentAdapter(options: FirestoreRecyclerOptions<Student>) :
    FirestoreRecyclerAdapter<Student, StudentFragmentAdapter.StudentHolder>(
        options
    ) {
    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_item, parent, false)

        return StudentHolder(view)
    }

    override fun onBindViewHolder(
        holder: StudentHolder,
        position: Int,
        model: Student
    ) {
            holder.name.text = model.name
            holder.room.text = model.roomNo
        holder.layout.setOnClickListener(View.OnClickListener {
            listener!!.onItemClick(snapshots.getSnapshot(position).id)
        })
    }

    class StudentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.student_item_name)
        var room : TextView = itemView.findViewById(R.id.student_item_room)
        var layout: RelativeLayout = itemView.findViewById(R.id.student_item_layout)
    }

    interface OnItemClickListener {
        fun onItemClick(documentSnapshot: String)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

}


