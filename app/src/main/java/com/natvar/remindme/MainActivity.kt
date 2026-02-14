package com.natvar.remindme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.calendarRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // ૨૦૨૬ ના ૧૨ મહિનાની યાદી
        val monthsList = mutableListOf<Calendar>()
        for (i in 0..11) {
            val calendar = Calendar.getInstance()
            calendar.set(2026, i, 1)
            monthsList.add(calendar)
        }

        // અહીં આપણે એડેપ્ટર સેટ કરીશું (જે કામ હું તમને આગળ આપીશ)
        // recyclerView.adapter = CalendarAdapter(monthsList)
    }
}
