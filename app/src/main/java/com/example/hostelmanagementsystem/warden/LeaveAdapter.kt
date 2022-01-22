package com.example.hostelmanagementsystem.warden

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelmanagementsystem.R
import com.example.hostelmanagementsystem.data.LeaveModel
import com.example.hostelmanagementsystem.data.Register
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.button.MaterialButton

class LeaveAdapter(options: FirestoreRecyclerOptions<LeaveModel>) :
    FirestoreRecyclerAdapter<LeaveModel, LeaveAdapter.LeaveHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaveHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(
            R.layout.leave_it,
            parent, false
        )
        return LeaveHolder(v)
    }

    override fun onBindViewHolder(holder: LeaveHolder, position: Int, model: LeaveModel) {
        holder.leaveReason.text = model.reason
        holder.leaveStart.text = "Start:" + model.startDate
        holder.leaveEnd.text = "End: " + model.endDate
        holder.leaveReject.setOnClickListener(View.OnClickListener {
            reject(position)
        })
     }

    fun reject(position: Int) {
        snapshots.getSnapshot(position).reference.delete()
    }

    class LeaveHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var leaveReason : TextView = itemView.findViewById(R.id.leave_reason)
        var leaveStart : TextView = itemView.findViewById(R.id.leave_start)
        var leaveEnd : TextView = itemView.findViewById(R.id.leave_end)
        var leaveApprove : MaterialButton = itemView.findViewById(R.id.leave_approve)
        var leaveReject : MaterialButton = itemView.findViewById(R.id.leave_reject)
    }
}