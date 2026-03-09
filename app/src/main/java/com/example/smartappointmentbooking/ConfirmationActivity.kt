package com.example.smartappointmentbooking

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ConfirmationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)

        val fullName = intent.getStringExtra("fullName") ?: ""
        val phone = intent.getStringExtra("phone") ?: ""
        val email = intent.getStringExtra("email") ?: ""
        val appointmentType = intent.getStringExtra("appointmentType") ?: ""
        val appointmentDate = intent.getStringExtra("appointmentDate") ?: ""
        val appointmentTime = intent.getStringExtra("appointmentTime") ?: ""
        val gender = intent.getStringExtra("gender") ?: ""

        findViewById<TextView>(R.id.tvSummaryFullName).text = "Full Name: $fullName"
        findViewById<TextView>(R.id.tvSummaryPhone).text = "Phone Number: $phone"
        findViewById<TextView>(R.id.tvSummaryEmail).text = "Email Address: $email"
        findViewById<TextView>(R.id.tvSummaryType).text = "Appointment Type: $appointmentType"
        findViewById<TextView>(R.id.tvSummaryDate).text = "Appointment Date: $appointmentDate"
        findViewById<TextView>(R.id.tvSummaryTime).text = "Appointment Time: $appointmentTime"
        findViewById<TextView>(R.id.tvSummaryGender).text = "Gender: $gender"
    }
}
