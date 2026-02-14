package com.natvar.remindme

import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class CalendarAdapter(private val months: List<Calendar>) :
    RecyclerView.Adapter<CalendarAdapter.MonthViewHolder>() {

    class MonthViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val monthTitle: TextView = view.findViewById(R.id.monthTitle)
        val daysGrid: GridView = view.findViewById(R.id.daysGrid)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_month, parent, false)
        return MonthViewHolder(view)
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
        val calendar = months[position]
        val monthFormat = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
        holder.monthTitle.text = monthFormat.format(calendar.time).uppercase()

        val days = mutableListOf<String>()
        val tempCal = calendar.clone() as Calendar
        tempCal.set(Calendar.DAY_OF_MONTH, 1)
        
        // મહિનાની શરૂઆતના ખાલી ખાના (Offset)
        val firstDayOfWeek = tempCal.get(Calendar.DAY_OF_WEEK) - 1
        for (i in 0 until firstDayOfWeek) {
            days.add("")
        }

        // મહિનાની તારીખો
        val daysInMonth = tempCal.getActualMaximum(Calendar.DAY_OF_MONTH)
        for (i in 1..daysInMonth) {
            days.add(i.toString())
        }

        holder.daysGrid.adapter = object : BaseAdapter() {
            override fun getCount(): Int = days.size
            override fun getItem(p: Int): Any = days[p]
            override fun getItemId(p: Int): Long = p.toLong()
            override fun getView(p: Int, convertView: View?, parent: ViewGroup?): View {
                val tv = TextView(holder.itemView.context)
                tv.text = days[p]
                tv.height = 100
                tv.gravity = Gravity.CENTER
                tv.textSize = 16f
                
                // રવિવાર લાલ રંગમાં (દર 7મો દિવસ, જો ખાલી ખાના ગણીએ તો)
                if (p % 7 == 0 && days[p].isNotEmpty()) {
                    tv.setTextColor(Color.RED)
                } else {
                    tv.setTextColor(Color.BLACK)
                }
                return tv
            }
        }
    }

    override fun getItemCount(): Int = months.size
}

