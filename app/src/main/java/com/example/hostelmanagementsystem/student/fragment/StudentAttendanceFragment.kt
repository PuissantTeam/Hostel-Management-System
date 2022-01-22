package com.example.hostelmanagementsystem.student.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.*

import android.os.Bundle

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hostelmanagementsystem.R
import com.example.hostelmanagementsystem.common.data.Prefs
import com.example.hostelmanagementsystem.databinding.FragmentStudentAttendanceBinding
import com.example.hostelmanagementsystem.extensions.closeKeyboard
import com.example.hostelmanagementsystem.extensions.showSnackBar
import com.example.hostelmanagementsystem.extensions.timer
import com.example.hostelmanagementsystem.student.adapter.StudentAttendanceAdapter
import com.example.hostelmanagementsystem.student.model.StudentAttendanceModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import java.text.SimpleDateFormat
import java.util.*

lateinit var permissionHelper: FragmentPermissionHelper

class StudentAttendanceFragment : Fragment(), PermissionListener {

    private lateinit var studentAttendanceBinding: FragmentStudentAttendanceBinding
    private lateinit var prefs: Prefs
    private val cal = Calendar.getInstance()
    private var currentDate: Int = cal.get(Calendar.DATE)
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val attendanceRef: CollectionReference = db.collection("User")
    private lateinit var anchorView: BottomNavigationView
    val TAG: String = StudentAttendanceFragment::class.java.name
    private lateinit var attendanceAdapter: StudentAttendanceAdapter
    private lateinit var studentAttendanceModel: StudentAttendanceModel
    private lateinit var userLatitude: String
    private lateinit var userLongitude: String

    private var locationListener: LocationListener? = null
    private var checkIfLocationHostel: Boolean = false
    private lateinit var date: String

    var gpsStatus: Boolean = false

    private val appPerms = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        studentAttendanceBinding = FragmentStudentAttendanceBinding.inflate(layoutInflater)

        studentAttendanceBinding.studentName.isSelected = true
        val rootView = studentAttendanceBinding.root

        anchorView = requireActivity().findViewById(R.id.student_bottom_navigation)

        prefs = Prefs(requireContext())
        val currentUser = FirebaseAuth.getInstance().currentUser

        Firebase.firestore.collection("User").document(currentUser!!.uid).get().addOnCompleteListener {
            val doc = it.result
            if (doc != null) {
                studentAttendanceBinding.studentName.text = "Hello there, ${doc.getString("name").toString()}"
            }
        }.addOnFailureListener {
            Log.d(TAG, it.message.toString())
            showSnackBar(requireActivity(), it.message, anchorView)
        }
        val calendar = Calendar.getInstance().time

        date = SimpleDateFormat("dd-MMM", Locale.ENGLISH).format(calendar)
            .toString().replace("-", " ")

        // check if today is a new day
        addAttendanceItem(date)

        val user = FirebaseAuth.getInstance().currentUser

        val queryAttendance =
            attendanceRef.document(user!!.uid).collection("Attendance")
                .orderBy("date", Query.Direction.DESCENDING)
        val optionsAttendance: FirestoreRecyclerOptions<StudentAttendanceModel> =
            FirestoreRecyclerOptions.Builder<StudentAttendanceModel>()
                .setQuery(queryAttendance, StudentAttendanceModel::class.java).build()

        attendanceAdapter = StudentAttendanceAdapter(optionsAttendance)
        studentAttendanceBinding.attendanceRecyclerView.setHasFixedSize(true)
        studentAttendanceBinding.attendanceRecyclerView.layoutManager = LinearLayoutManager(context)
        studentAttendanceBinding.attendanceRecyclerView.adapter = attendanceAdapter


        val dateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())

        studentAttendanceBinding.todaysDate.text =
            dateFormat.format(calendar).toString().replace("-", " ")
        studentAttendanceBinding.todaysDay.text =
            SimpleDateFormat("EEEE", Locale.ENGLISH).format(calendar)

        when {
            prefs.absent -> {
                studentAttendanceBinding.checkInButton.text = "ABSENT"
                studentAttendanceBinding.checkInButton.isEnabled = false
            }
            prefs.checkedOut -> {
                studentAttendanceBinding.checkInButton.text = "Check In"
            }
            !prefs.present -> {
                studentAttendanceBinding.checkInButton.text = "Check Out"
            }
            prefs.present -> {
                studentAttendanceBinding.checkInButton.text = "Present"
                studentAttendanceBinding.checkInButton.isEnabled = false
            }
        }
        studentAttendanceBinding.checkInButton.setOnClickListener {
            if (prefs.locationPermission) {
                val locationMangaer =
                    context?.getSystemService(Activity.LOCATION_SERVICE) as LocationManager
                locationListener = MyLocationListener()
                locationMangaer.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 5000, 10f,
                    locationListener!!
                )
                timer(studentAttendanceBinding.attendanceProgressBar, 400)
                if (checkIfLocationHostel) {
                    if (prefs.checkedOut) {
                        prefs.checkedIn = true
                        prefs.checkedOut = false
                        val checkInTime = studentAttendanceBinding.attendanceClock.text.toString()
                        prefs.currentDateCheckInTime = checkInTime
                        studentAttendanceBinding.checkInButton.text = "CHECK OUT"
                        setAttendance(date, prefs.currentDateCheckInTime!!, "", "CheckedIn")
                        timer(studentAttendanceBinding.attendanceProgressBar, 400)
                    } else {
                        prefs.present = true
                        val checkOutTime = studentAttendanceBinding.attendanceClock.text.toString()
                        timer(studentAttendanceBinding.attendanceProgressBar, 400)
                        setAttendance(
                            date,
                            prefs.currentDateCheckInTime!!,
                            checkOutTime,
                            "CheckedIn"
                        )

                        studentAttendanceBinding.checkInButton.text = "Present"
                        studentAttendanceBinding.checkInButton.isClickable = false
                    }
                }

            } else {
                locationEnabled()
                if (!gpsStatus)
                    showSnackBar(requireActivity(), "Please Enable Location", anchorView)
            }
        }


        return rootView
    }

//    @SuppressLint("MissingPermission")
//    private fun checkIfLocationHostel():Boolean{
//        val locationMangaer =
//            context?.getSystemService(Activity.LOCATION_SERVICE) as LocationManager
//        locationListener = MyLocationListener()
//        locationMangaer.requestLocationUpdates(
//            LocationManager.GPS_PROVIDER, 5000, 10f,
//            locationListener!!
//        )
//        return true
//    }

    private fun addAttendanceItem(date: String) {
        if (currentDate != prefs.lastVisitedDateTime) {
            prefs.checkedIn = false
            prefs.checkedOut = true
            prefs.present = false
            studentAttendanceBinding.checkInButton.isEnabled = true
            prefs.lastVisitedDateTime = currentDate
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                val studentAttendanceModel =
                    StudentAttendanceModel(date, "", "", "absent")
                Firebase.firestore.collection("User").document(user.uid)
                    .collection("Attendance")
                    .add(studentAttendanceModel)
                    .addOnSuccessListener { addTask ->
                        prefs.currentDateAttendanceId = addTask.id
                        Log.d(TAG, "Attendance for $date item added ")
                    }.addOnFailureListener { addTask ->
                        showSnackBar(requireActivity(), addTask.message, anchorView)
                    }
            }
        }
    }

    private fun setAttendance(
        date: String,
        checkIn: String,
        checkOut: String,
        status: String
    ) {
        studentAttendanceModel =
            StudentAttendanceModel(
                date,
                checkIn,
                checkOut,
                status
            )
        val user = FirebaseAuth.getInstance().currentUser
        Firebase.firestore.collection("User").document(user?.uid.toString())
            .collection("Attendance")
            .document(prefs.currentDateAttendanceId!!)
            .set(studentAttendanceModel)
            .addOnCompleteListener {
                Log.d(TAG, "Attendance Set")
            }.addOnFailureListener { setTask ->
                showSnackBar(requireActivity(), setTask.message, anchorView)
            }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        permissionHelper = FragmentPermissionHelper(this, this)
        permissionHelper.checkForMultiplePermissions(appPerms)
    }

    // request location
    override fun shouldShowRationaleInfo() {

        val dialogBuilder = AlertDialog.Builder(requireContext())

        dialogBuilder.setMessage("Camera permission is Required")
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton("OK") { dialog, id ->
                dialog.cancel()
                permissionHelper.launchPermissionDialogForMultiplePermissions(appPerms)
            }

            .setNegativeButton("Cancel") { dialog, id ->
                dialog.cancel()
                showSnackBar(requireActivity(), "Attendance requires Location Access", anchorView)
                findNavController().navigate(R.id.studentDashboardFragment)
            }

        val alert = dialogBuilder.create()

        alert.setTitle("Location Request")

        alert.show()

    }


    override fun isPermissionGranted(isGranted: Boolean) {
        if (isGranted) {
            prefs.locationPermission = true
            Log.d(TAG, "Location Permission Given")
        } else {
            prefs.locationPermission = false
            showSnackBar(requireActivity(), "Attendance requires Location Access", anchorView)
            findNavController().navigate(R.id.studentDashboardFragment)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    Log.d(TAG, "Location Permission Given")
                }
            } else {
                showSnackBar(requireActivity(), "Attendance requires Location Access", anchorView)
            }
        }
    }

    private fun locationEnabled() {
        val locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        gpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    override fun onStart() {
        super.onStart()
        attendanceAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        attendanceAdapter.stopListening()
        closeKeyboard()
    }

    /*----------Listener class to get coordinates ------------- */
    private inner class MyLocationListener : LocationListener {
        override fun onLocationChanged(loc: Location) {
            userLatitude = loc.latitude.toString()
            userLongitude = loc.longitude.toString()
            Log.d(TAG, loc.longitude.toString())
            Log.d(TAG, loc.latitude.toString())

            if (userLatitude == hostelLatitude && userLongitude == hostelLongitude) {
                checkIfLocationHostel = true

                showSnackBar(
                    requireActivity(),
                    "Student is in hostel campus, Click again to checkIn/Out",
                    anchorView
                )
            } else {
                prefs.absent = true
                studentAttendanceBinding.checkInButton.text = "ABSENT"
                studentAttendanceBinding.checkInButton.isEnabled = false
                showSnackBar(
                    requireActivity(),
                    "Student is not in hostel campus, marking absent",
                    anchorView
                )
                setAttendance(date, "ABSENT", "ABSENT", "Absent")
            }
        }
    }

    companion object {
        const val hostelLatitude = "19.2061"
        const val hostelLongitude = "72.8726"
        const val emulatorLongitude = "-122.084"
        const val emulatorLatitude = "37.421998333333335"
    }
}

