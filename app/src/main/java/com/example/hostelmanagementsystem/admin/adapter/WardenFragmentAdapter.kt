package com.example.hostelmanagementsystem.admin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelmanagementsystem.R
import com.example.hostelmanagementsystem.admin.model.AdminWarden
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class WardenFragmentAdapter(options: FirestoreRecyclerOptions<AdminWarden>) :
    FirestoreRecyclerAdapter<AdminWarden, WardenFragmentAdapter.WardenHolder>(
        options
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WardenHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.admin_item_warden, parent, false)

        return WardenHolder(view)
    }
    override fun onBindViewHolder(holder: WardenHolder, position: Int, model: AdminWarden) {
        holder.name.text = model.name
    }


        class WardenHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var name: TextView = itemView.findViewById(R.id.warden_item_name)

        }


}
