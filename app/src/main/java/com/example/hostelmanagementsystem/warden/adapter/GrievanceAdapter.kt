package com.example.hostelmanagementsystem.warden.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelmanagementsystem.R
import com.example.hostelmanagementsystem.extensions.NotificationInterface
import com.example.hostelmanagementsystem.student.model.GrievanceModel
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

class GrievanceAdapter(options: FirestoreRecyclerOptions<GrievanceModel>) :
    FirestoreRecyclerAdapter<GrievanceModel, GrievanceAdapter.GrievanceHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrievanceHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(
            R.layout.leave_grievance_item,
            parent, false
        )
        return GrievanceHolder(v)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: GrievanceHolder, position: Int, model: GrievanceModel) {
        holder.title.text = model.title
        holder.desc.text = model.description
        holder.name.text = model.name
        if(model.status.equals("1")){
            holder.resolve.text = "Resolved"
            holder.resolve.backgroundTintList = ColorStateList.valueOf(R.color.grey)
            holder.resolve.isClickable = false
        }
        holder.resolve.setOnClickListener(View.OnClickListener {
            // Create Retrofit
            val retrofit = Retrofit.Builder()
                .baseUrl("https://hostel-management-system062.herokuapp.com/api/")
                .build()

            // Create Service
            val service = retrofit.create(NotificationInterface::class.java)

            // Create JSON using JSONObject
            val jsonObject = JSONObject()
            jsonObject.put("uid", model.uid)
            jsonObject.put("title", "Your grievance has been resolved")
            jsonObject.put("body", model.title)

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
            resolve(position)
        })
    }

    private fun resolve(position: Int) {
        snapshots.getSnapshot(position).reference.update("status", "1")
    }

    class GrievanceHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title : TextView = itemView.findViewById(R.id.grievance_title)
        var desc : TextView = itemView.findViewById(R.id.grievance_desc)
        var name : TextView = itemView.findViewById(R.id.grievance_name)
        var resolve : MaterialButton = itemView.findViewById(R.id.grievance_btn)
    }
}