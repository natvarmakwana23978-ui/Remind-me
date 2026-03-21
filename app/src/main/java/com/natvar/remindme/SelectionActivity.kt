package com.natvar.remindme

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)

        val spinnerLanguage = findViewById<Spinner>(R.id.spinnerLanguage)
        val spinnerCalendar = findViewById<Spinner>(R.id.spinnerCalendar)
        val btnNext = findViewById<Button>(R.id.btnNext)

        // ૧. ભાષાનું લિસ્ટ (ઉદાહરણ મુજબ)
        val languages = arrayOf("ગુજરાતી", "Hindi", "English", "Tamil", "Arabic")
        val langAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, languages)
        spinnerLanguage.adapter = langAdapter

        // ૨. ૨૭ કેલેન્ડરનું લિસ્ટ [cite: 2026-02-07]
        val calendars = arrayOf(
            "વિક્રમ સંવત (Gujarati)", "Islamic (Hijri)", "Chinese Calendar", 
            "Hebrew Calendar", "Buddhist Calendar", "Tamil Calendar", "English Calendar"
        )
        val calAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, calendars)
        spinnerCalendar.adapter = calAdapter

        btnNext.setOnClickListener {
            val selectedLang = spinnerLanguage.selectedItem.toString()
            val selectedCal = spinnerCalendar.selectedItem.toString()

            // પસંદગી સેવ કરવી
            val sharedPref = getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("user_language", selectedLang)
                putString("user_calendar", selectedCal)
                apply()
            }

            // આગળની સ્ક્રીન પર જવું (MainActivity અથવા Calendar Builder)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
