package com.example.hostelmanagementsystem.activities

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.hostelmanagementsystem.data.*
import com.example.hostelmanagementsystem.R
import com.example.hostelmanagementsystem.databinding.ActivityRegisterBinding
import com.example.hostelmanagementsystem.extensions.showSnackBar
import com.example.hostelmanagementsystem.onboarding.OnBoardingActivity
import com.example.hostelmanagementsystem.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val state_list = java.util.ArrayList<String>()
    private val district_list = java.util.ArrayList<DistrictItems>()
    private val district_name_list = java.util.ArrayList<String>()
    private lateinit var requestOueue: RequestQueue
    private lateinit var etVolState: AutoCompleteTextView
    private lateinit var etVolDistrict: AutoCompleteTextView
    var regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$"
    val statesMap = hashMapOf(
        "Odisha" to 4183.42,
        "Assam" to 3175.35,
        "Nicobar" to 2274.33,
        "Arunachal Pradesh" to 2080.62,
        "Nagaland" to 1971.88,
        "Manipur" to 1882.34,
        "Andaman and Nicobar Island" to 1871.39,
        "South Andaman" to 1868.71,
        "North and Middle Andaman" to 1841.14,
        "Mizoram" to 1742.58,
        "Ladakh" to 1705.91,
        "Tripura" to 1694.5,
        "Jammu and Kashmir" to 1675.97,
        "Meghalaya" to 1673.1,
        "Sikkim" to 1516.08,
        "Himachal Pradesh" to 1446.44,
        "Punjab" to 1368.08,
        "Chandigarh" to 1324.94,
        "West Bengal" to 1301.73,
        "Uttarakhand" to 1205.99,
        "Haryana" to 1166.65,
        "Bihar" to 1146.17,
        "Delhi" to 1092.27,
        "Jharkhand" to 1066.14,
        "Lakshadweep" to 1060.28,
        "Uttar Pradesh" to 1015.69,
        "Kerala" to 922.63,
        "Rajasthan" to 917.69,
        "Tamil Nadu" to 904.29,
        "Puducherry" to 830.97,
        "Gujarat" to 696.44,
        "Chhattisgarh" to 669.89,
        "Madhya Pradesh" to 597.86,
        "Andhra Pradesh" to 534.13,
        "Goa" to 485.28,
        "Karnataka" to 423.97,
        "Dadra and Nagar Haveli and Daman and Diu" to 423.14,
        "Telangana" to 308.58
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        requestOueue = Volley.newRequestQueue(this)
        stateJsonParse()
        binding.RegisterBtnRegister.setOnClickListener(View.OnClickListener {
            registerUser(view)
        })
        setContentView(view)
    }

    private fun registerUser(view: View) {
        val name = binding.nameRegister.text.toString().trim()
        val email = binding.registerEmail.text.toString().trim()
        val state = binding.etVolState.text.toString().trim()
        val district = binding.etVolDistrict.text.toString().trim()
        val sid = binding.registerID.text.toString().trim()
        if (name.isEmpty()) {
            binding.nameLayoutRegister.error = "Enter name"
            return
        }
        if (email.isEmpty()) {
            binding.emailLayoutRegister.error = "Enter email"
            return
        }
        if (!email.matches(regex.toRegex())) {
            binding.emailLayoutRegister.error = "Enter proper email"
            return
        }
        if (sid.isEmpty()) {
            binding.IDLayoutRegister.error = "Enter Student Id"
            return
        }
        if (sid.length < 8) {
            binding.IDLayoutRegister.error = "Invalid, Enter proper 8 digit sid"
            return
        }
        if (state.isEmpty()) {
            showSnackBar(this, "Select State", view)
            return
        }
        if (district.isEmpty()) {
            showSnackBar(this, "Select District", view)
            return
        }
        val distance = statesMap.get(state)
        //Create user with email and password
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, sid)
            .addOnSuccessListener {
                Log.d("test", "Created email")
                //Get uid and add to register
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, sid)
                    .addOnSuccessListener {
                        Log.d("test", "Signed in with email")
                        val uid = FirebaseAuth.getInstance().currentUser?.uid
                        val user = uid?.let { it1 ->
                            Register(
                                name,
                                it1,
                                sid,
                                email,
                                state,
                                district,
                                distance!!.toInt(),
                                "pending"
                            )
                        }
                        //Add user in register
                        if (user != null) {
                            FirebaseFirestore.getInstance().collection("Register").add(user)
                                .addOnSuccessListener {
                                    Log.d("test", "Added user to register")
                                    val alert: AlertDialog.Builder = AlertDialog.Builder(this)
                                    alert.setTitle("Registration Successful")
                                    alert.setView(R.layout.register_alert_layout)
                                    alert.setPositiveButton("Okay") { dialog, which ->
                                        dialog.dismiss()
                                        val intent = Intent(this, OnBoardingActivity::class.java)
                                        startActivity(intent)
                                    }
                                    alert.setCancelable(false)
                                    val dialog: AlertDialog = alert.create()
                                    dialog.show()
                                }.addOnFailureListener {
                                    showSnackBar(this, it.toString(), binding.fakeAnchorLayout)
                                }
                        }
                    }.addOnFailureListener {
                        showSnackBar(this, it.toString(), binding.fakeAnchorLayout)
                    }
                FirebaseAuth.getInstance().signOut()
            }.addOnFailureListener {
                showSnackBar(this, it.toString(), binding.fakeAnchorLayout)
            }
    }

    private fun stateJsonParse() {
        val url = Constants.STATE_URL

        val stateString = StringRequest(url, { str ->
            val state_class = Gson().fromJson(str, State::class.java)
            for (i in state_class.states.indices) {
                state_list.add(state_class.states[i].state_name)
            }
            Log.d("test", state_class.states[1].state_name)
            val stateAdapter =
                ArrayAdapter(this, R.layout.dropdown_item, state_list)
            binding.etVolState.setAdapter(stateAdapter)
            binding.etVolState.setOnItemClickListener { _, _, position, _ ->
                Toast.makeText(this, state_list[position], Toast.LENGTH_SHORT).show()
                binding.etVolDistrict.isEnabled = true
                district_list.clear()
                district_name_list.clear()
                districtJsonParse(position)
            }
            Log.d("chk_state", str)
        }, {
            Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
        })
        requestOueue.add(stateString)
    }

    private fun districtJsonParse(position: Int) {

        val url = "${Constants.DISTRICT_URL}${position}"
        Log.d("chk_url", url)
        val districtString = StringRequest(url, { str ->
            val district_class = Gson().fromJson(str, District::class.java)
            for (i in district_class.districts!!.indices) {
                district_list.add(district_class.districts[i]!!)
                district_name_list.add(district_class.districts[i]!!.district_name!!)
            }
            val stateAdapter =
                ArrayAdapter(this, R.layout.dropdown_item, district_name_list)
            binding.etVolDistrict.apply {
                setAdapter(stateAdapter)
                setOnItemClickListener { _, _, position, _ ->
//                    txtVolInfo.visibility = View.VISIBLE
//                    txtVolInfo.text = "Fetching data..."
//                    addItemToList()
                }
            }
            Log.d("chk_state", str)
        }, {
        })
        requestOueue.add(districtString)
    }
}