package com.example.hostelmanagementsystem.warden

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelmanagementsystem.data.Register
import com.example.hostelmanagementsystem.data.Room
import com.example.hostelmanagementsystem.data.Student
import com.example.hostelmanagementsystem.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AllocationAdapter(options: FirestoreRecyclerOptions<Register>) :
    FirestoreRecyclerAdapter<Register, AllocationAdapter.AllocationHolder>(options) {
    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllocationHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(
            R.layout.allocation_item,
            parent, false
        )
        return AllocationHolder(v)
    }

    override fun onBindViewHolder(holder: AllocationHolder, position: Int, model: Register) {
        holder.allocationName.text = model.name
        holder.allocationSid.text = model.sid
        """${model.district}, ${model.state}""".also { holder.allocationPlace.text = it }

        holder.allocationApprove.setOnClickListener(View.OnClickListener {
            //Get room no
            FirebaseFirestore.getInstance().collection("Room")
                .whereEqualTo("status", "empty").get()
                .addOnSuccessListener { query ->
                    val roomNo = query.documents[0].getString("roomNo")
                    val room = roomNo?.let { roomNoit -> Room("alloted", roomNoit, model.uid, model.name) }
                    if (room != null) {
                        //Update room details
                        FirebaseFirestore.getInstance().collection("Room")
                            .document(query.documents[0].id).set(room)
                            .addOnCompleteListener { removeRoom ->
                                Log.d("test", "Room done")
                            }
                            .addOnFailureListener { exceptionRoom ->
                                Log.d("test", exceptionRoom.toString())
                            }
                    }
                    val user = roomNo?.let { it1 ->
                        Student(
                            "student",
                            model.name,
                            model.uid,
                            model.sid,
                            model.email,
                            model.state,
                            model.district,
                            it1
                        )
                    }

                    //Create user in User collection
                    if (user != null) {
                        FirebaseFirestore.getInstance().collection("User")
                            .document(model.uid).set(user)
                            .addOnCompleteListener { sendEmail ->
                                //Send verfication link to student
                                FirebaseAuth.getInstance().sendPasswordResetEmail(model.email)
                                    .addOnCompleteListener { link ->
                                        Log.d("test", "Send email")
                                        Toast.makeText(
                                            holder.itemView.context,
                                            "Alloted",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        allot(position)
                                        listener!!.onItemClick(snapshots.getSnapshot(position).id)
                                    }
                            }.addOnFailureListener { linkException ->
                                Log.d("test", linkException.toString())
                            }
                    }

                }.addOnFailureListener { exception ->
                    Log.d("test", exception.toString())
                }
            return@OnClickListener
        })
    }

    fun allot(position: Int) {
        snapshots.getSnapshot(position).reference.delete()
    }

    class AllocationHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var allocationName: TextView = itemView.findViewById(R.id.allocation_name)
        var allocationSid: TextView = itemView.findViewById(R.id.allocation_sid)
        var allocationPlace: TextView = itemView.findViewById(R.id.allocation_place)
        var allocationApprove: MaterialButton = itemView.findViewById(R.id.allocation_approve)
    }

    interface OnItemClickListener {
        fun onItemClick(documentSnapshot: String)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }
}