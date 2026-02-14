package com.natvar.remindme

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.Typeface
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
        holder.monthTitle.text = monthFormat.format(calendar.time)

        val days = mutableListOf<String>()
        val tempCal = calendar.clone() as Calendar
        tempCal.set(Calendar.DAY_OF_MONTH, 1)
        
        val firstDayOfWeek = tempCal.get(Calendar.DAY_OF_WEEK) - 1
        for (i in 0 until firstDayOfWeek) {
            days.add("")
        }

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
                tv.height = 110
                tv.gravity = Gravity.CENTER
                tv.textSize = 18f
                tv.typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                
                if (days[p].isNotEmpty()) {
                    if (p % 7 == 0) {
                        tv.setTextColor(Color.parseColor("#E91E63"))
                        tv.setTypeface(null, Typeface.BOLD)
                    } else {
                        tv.setTextColor(Color.parseColor("#424242"))
                    }

                    // તારીખ પર ક્લિક કરવાથી રિમાઇન્ડર બતાવવું
                    tv.setOnClickListener {
                        showReminderDialog(holder.itemView.context, days[p], monthFormat.format(calendar.time))
                    }
                }
                return tv
            }
        }
    }

    // રિમાઇન્ડર લિસ્ટ બતાવવા માટેનું ફંક્શન
    private fun showReminderDialog(context: android.content.Context, day: String, monthYear: String) {
        val reminders = mutableListOf<String>()
        
        // ૧. તારીખ મુજબના ખાસ તહેવારો (તમારા પ્લાન મુજબ)
        if (monthYear == "February 2026" && day == "14") {
            reminders.add("💘 Happy Valentine's Day")
        }
        if (monthYear == "March 2026" && day == "4") {
            reminders.add("🎨 Happy Holi")
        }

        // ૨. સામાન્ય રિમાઇન્ડર્સ જે લિસ્ટમાં હંમેશા રહે છે
        reminders.add("📅 File Income Tax Return")
        reminders.add("🛒 Purchase Grocery")
        reminders.add("🏥 Go to hospital for health check up")
        reminders.add("🎂 Happy Birthday")

        // ડાયલોગ બોક્સ બનાવવો
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Reminders: $day $monthYear")
        
        val reminderText = reminders.joinToString("\n\n• ")
        builder.setMessage("• $reminderText")
        
        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        builder.show()
    }

    override fun getItemCount(): Int = months.size
}
