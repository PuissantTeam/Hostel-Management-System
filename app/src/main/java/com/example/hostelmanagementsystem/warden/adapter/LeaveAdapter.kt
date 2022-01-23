package com.example.hostelmanagementsystem.warden.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelmanagementsystem.R
import com.example.hostelmanagementsystem.extensions.NotificationInterface
import com.example.hostelmanagementsystem.student.model.LeaveModel
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit

class LeaveAdapter(options: FirestoreRecyclerOptions<LeaveModel>) :
    FirestoreRecyclerAdapter<LeaveModel, LeaveAdapter.LeaveHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaveHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(
            R.layout.leave_item,
            parent, false
        )
        return LeaveHolder(v)
    }

    override fun onBindViewHolder(holder: LeaveHolder, position: Int, model: LeaveModel) {
        holder.leaveReason.text = model.reason
        ("Start:" + model.startDate).also { holder.leaveStart.text = it }
        ("End: " + model.endDate).also { holder.leaveEnd.text = it }
        if(model.status.equals("1")){
            holder.leaveApprove.visibility = View.GONE
            holder.leaveReject.visibility = View.GONE
            holder.leaveStatus.visibility = View.VISIBLE
            holder.leaveStatus.text = "Approved"
        }
        if(model.status.equals("-1")){
            holder.leaveApprove.visibility = View.GONE
            holder.leaveReject.visibility = View.GONE
            holder.leaveStatus.visibility = View.VISIBLE
            holder.leaveStatus.text = "Rejected"
        }
        holder.leaveReject.setOnClickListener(View.OnClickListener {
            snapshots.getSnapshot(position).reference.update("status", "-1")
            sendNotifications("rejected", model.uid, model.reason)
        })
        holder.leaveApprove.setOnClickListener(View.OnClickListener {
            snapshots.getSnapshot(position).reference.update("status", "1")
            sendNotifications("approved", model.uid, model.reason)
        })
     }

    fun sendNotifications(status: String, uid: String, reason: String ){
        // Create Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://hostel-management-system062.herokuapp.com/api/")
            .build()

        // Create Service
        val service = retrofit.create(NotificationInterface::class.java)

        // Create JSON using JSONObject
        val jsonObject = JSONObject()
        jsonObject.put("uid", uid)
        jsonObject.put("title", "Your Leave Application has been $status")
        jsonObject.put("body", reason)

        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()

        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            // Do the POST request and get response
            val response = service.createPost(requestBody)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Log.d("My", "done")

                } else {
                    Log.e("My", response.toString())
                }
            }
        }
    }

    class LeaveHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var leaveReason : TextView = itemView.findViewById(R.id.leave_reason)
        var leaveStart : TextView = itemView.findViewById(R.id.leave_start)
        var leaveEnd : TextView = itemView.findViewById(R.id.leave_end)
        var leaveApprove : MaterialButton = itemView.findViewById(R.id.leave_approve)
        var leaveReject : MaterialButton = itemView.findViewById(R.id.leave_reject)
        var leaveStatus: TextView = itemView.findViewById(R.id.leave_status)
    }
}