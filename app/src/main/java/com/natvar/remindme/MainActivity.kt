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

        // XML માંથી RecyclerView ને જોડવું
        val recyclerView: RecyclerView = findViewById(R.id.calendarRecyclerView)
        
        // લિસ્ટને ઉપર-નીચે (Vertical) સ્ક્રોલ કરવા માટે સેટિંગ
        recyclerView.layoutManager = LinearLayoutManager(this)

        // ૨૦૨૬ ના ૧૨ મહિના (જાન્યુઆરી થી ડિસેમ્બર) તૈયાર કરવા
        val monthsList = mutableListOf<Calendar>()
        for (i in 0..11) {
            val calendar = Calendar.getInstance()
            calendar.set(2026, i, 1) // વર્ષ ૨૦૨૬, મહિનો i, અને તારીખ ૧
            monthsList.add(calendar)
        }

        // એડેપ્ટર દ્વારા ડેટાને સ્ક્રીન પર બતાવવો
        recyclerView.adapter = CalendarAdapter(monthsList)
    }
}
