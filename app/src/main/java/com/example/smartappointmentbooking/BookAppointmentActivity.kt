package com.example.smartappointmentbooking

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class BookAppointmentActivity : AppCompatActivity() {

    private lateinit var etFullName: EditText
    private lateinit var etPhone: EditText
    private lateinit var etEmail: EditText
    private lateinit var spinnerAppointmentType: Spinner
    private lateinit var tvSelectedDate: TextView
    private lateinit var tvSelectedTime: TextView
    private lateinit var rgGender: RadioGroup
    private lateinit var cbTerms: CheckBox

    private var selectedDate: String = "Not Selected"
    private var selectedTime: String = "Not Selected"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_appointment)

        bindViews()
        setupSpinner()
        setupDatePicker()
        setupTimePicker()
        setupConfirmButton()
    }

    private fun bindViews() {
        etFullName = findViewById(R.id.etFullName)
        etPhone = findViewById(R.id.etPhone)
        etEmail = findViewById(R.id.etEmail)
        spinnerAppointmentType = findViewById(R.id.spinnerAppointmentType)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvSelectedTime = findViewById(R.id.tvSelectedTime)
        rgGender = findViewById(R.id.rgGender)
        cbTerms = findViewById(R.id.cbTerms)
    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.appointment_types,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerAppointmentType.adapter = adapter
    }

    private fun setupDatePicker() {
        findViewById<Button>(R.id.btnPickDate).setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    selectedDate = "%02d/%02d/%04d".format(dayOfMonth, month + 1, year)
                    tvSelectedDate.text = selectedDate
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun setupTimePicker() {
        findViewById<Button>(R.id.btnPickTime).setOnClickListener {
            val calendar = Calendar.getInstance()
            TimePickerDialog(
                this,
                { _, hourOfDay, minute ->
                    selectedTime = "%02d:%02d".format(hourOfDay, minute)
                    tvSelectedTime.text = selectedTime
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        }
    }

    private fun setupConfirmButton() {
        findViewById<Button>(R.id.btnConfirmBooking).setOnClickListener {
            if (!validateForm()) {
                return@setOnClickListener
            }

            val selectedGenderId = rgGender.checkedRadioButtonId
            val gender = if (selectedGenderId != -1) {
                findViewById<android.widget.RadioButton>(selectedGenderId).text.toString()
            } else {
                "Not Selected"
            }

            val intent = Intent(this, ConfirmationActivity::class.java).apply {
                putExtra("fullName", etFullName.text.toString().trim())
                putExtra("phone", etPhone.text.toString().trim())
                putExtra("email", etEmail.text.toString().trim())
                putExtra("appointmentType", spinnerAppointmentType.selectedItem.toString())
                putExtra("appointmentDate", selectedDate)
                putExtra("appointmentTime", selectedTime)
                putExtra("gender", gender)
            }
            startActivity(intent)
        }
    }

    private fun validateForm(): Boolean {
        val name = etFullName.text.toString().trim()
        val phone = etPhone.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val appointmentTypePosition = spinnerAppointmentType.selectedItemPosition

        if (name.isEmpty()) {
            etFullName.error = "Name must not be empty"
            etFullName.requestFocus()
            return false
        }

        if (phone.isEmpty()) {
            etPhone.error = "Phone number must not be empty"
            etPhone.requestFocus()
            return false
        }

        if (email.isEmpty()) {
            etEmail.error = "Email must not be empty"
            etEmail.requestFocus()
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Please enter a valid email address"
            etEmail.requestFocus()
            return false
        }

        if (appointmentTypePosition == 0) {
            Toast.makeText(this, "Please select appointment type", Toast.LENGTH_SHORT).show()
            return false
        }

        if (selectedDate == "Not Selected") {
            Toast.makeText(this, "Please select appointment date", Toast.LENGTH_SHORT).show()
            return false
        }

        if (selectedTime == "Not Selected") {
            Toast.makeText(this, "Please select appointment time", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!cbTerms.isChecked) {
            Toast.makeText(this, "Please accept terms and conditions", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}
