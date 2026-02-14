package com.natvar.remindme

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calendarGrid: GridView = findViewById(R.id.calendarGrid)
        val calendarHeader: TextView = findViewById(R.id.calendarHeader)

        // અત્યારનો મહિનો અને વર્ષ મેળવવા
        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH) + 1
        val currentYear = 2026

        calendarHeader.text = "FEBRUARY 2026"

        // આપણે અગાઉ બનાવેલા CalendarLogic નો ઉપયોગ કરીને તારીખો મેળવવી
        val logic = CalendarLogic()
        val dates = logic.getDatesInMonth(currentYear, currentMonth)
        
        // એન્ડ્રોઇડ લિસ્ટમાં બતાવવા માટે માત્ર તારીખોના આંકડા અલગ કરવા
        val dateStrings = mutableListOf<String>()
        
        // મહિનાની શરૂઆતના ખાલી ખાના (Spaces) ઉમેરવા
        val startDay = Calendar.getInstance().apply { set(2026, 1, 1) }.get(Calendar.DAY_OF_WEEK)
        for (i in 1 until startDay) {
            dateStrings.add("")
        }

        dates.forEach { dateStrings.add(it.dayOfMonth.toString()) }

        // એડેપ્ટર દ્વારા ગ્રીડમાં તારીખો સેટ કરવી
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dateStrings)
        calendarGrid.adapter = adapter
    }
}

