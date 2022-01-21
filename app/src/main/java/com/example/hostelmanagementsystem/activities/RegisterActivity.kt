package com.example.hostelmanagementsystem.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.hostelmanagementsystem.Data.District
import com.example.hostelmanagementsystem.Data.DistrictItems
import com.example.hostelmanagementsystem.Data.State
import com.example.hostelmanagementsystem.R
import com.example.hostelmanagementsystem.databinding.ActivityRegisterBinding
import com.example.hostelmanagementsystem.utils.Constants
import com.google.gson.Gson

class RegisterActivity : AppCompatActivity() {
    val state_list = java.util.ArrayList<String>()
    val district_list = java.util.ArrayList<DistrictItems>()
    val district_name_list = java.util.ArrayList<String>()
    lateinit var requestOueue: RequestQueue
    private lateinit var etVolState : AutoCompleteTextView
    private lateinit var etVolDistrict : AutoCompleteTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        requestOueue = Volley.newRequestQueue(this)
        etVolState = findViewById(R.id.etVolState)
        etVolDistrict = findViewById(R.id.etVolDistrict)
        stateJsonParse()
    }
    private fun stateJsonParse(){
        val url = Constants.STATE_URL

        val stateString = StringRequest(url, { str ->
            val state_class = Gson().fromJson(str, State::class.java)
            for (i in state_class.states.indices) {
                state_list.add(state_class.states[i].state_name)
            }
            Log.d("test", state_class.states[1].state_name)
            val stateAdapter =
                ArrayAdapter(this, R.layout.dropdown_item, state_list)
            etVolState.setAdapter(stateAdapter)
            etVolState.setOnItemClickListener { _, _, position, _ ->
                Toast.makeText(this, state_list[position], Toast.LENGTH_SHORT).show()
                etVolDistrict.isEnabled = true
                district_list.clear()
                district_name_list.clear()
                districtJsonParse(position)
            }
            Log.d("chk_state", str)
        }, {
            Toast.makeText(this , it.toString(), Toast.LENGTH_LONG).show()
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
            etVolDistrict.apply {
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