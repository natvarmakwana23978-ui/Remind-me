package com.natvar.remindme

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_month, parent, false)
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
        for (i in 0 until firstDayOfWeek) days.add("")
        val daysInMonth = tempCal.getActualMaximum(Calendar.DAY_OF_MONTH)
        for (i in 1..daysInMonth) days.add(i.toString())

        holder.daysGrid.adapter = object : BaseAdapter() {
            override fun getCount(): Int = days.size
            override fun getItem(p: Int): Any = days[p]
            override fun getItemId(p: Int): Long = p.toLong()
            
            override fun getView(p: Int, convertView: View?, parent: ViewGroup?): View {
                val tv = TextView(holder.itemView.context)
                tv.text = days[p]
                tv.height = 120
                tv.gravity = Gravity.CENTER
                tv.textSize = 18f
                tv.typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)

                if (days[p].isNotEmpty()) {
                    val dayNum = days[p].toInt()
                    val festival = getFestival(dayNum, monthFormat.format(calendar.time))
                    
                    // રવિવાર અને ૨-૪ શનિવાર લાલ
                    val isSunday = (p % 7 == 0)
                    val isSaturday = (p % 7 == 6)
                    if (isSunday || (isSaturday && (dayNum in 8..14 || dayNum in 22..28))) {
                        tv.setTextColor(Color.parseColor("#D32F2F"))
                    }

                    // તહેવાર હાઈલાઈટ
                    if (festival.isNotEmpty()) {
                        val shape = GradientDrawable()
                        shape.cornerRadius = 15f
                        shape.setColor(Color.parseColor("#FFF9C4"))
                        tv.background = shape
                    }

                    tv.setOnClickListener {
                        showReminderDialog(holder.itemView.context, days[p], monthFormat.format(calendar.time))
                    }
                }
                return tv
            }
        }
    }

    // તહેવારોનું લિસ્ટ (તમારા સ્ક્રીનશોટ મુજબ)
    private fun getFestival(day: Int, monthYear: String): String {
        return when (monthYear) {
            "January 2026" -> when(day) { 1 -> "New Year"; 14 -> "Uttarayan"; 26 -> "Republic Day"; else -> "" }
            "February 2026" -> when(day) { 14 -> "Valentine's Day"; 15 -> "Maha Shivratri"; else -> "" }
            else -> ""
        }
    }

    private fun showReminderDialog(context: Context, day: String, monthYear: String) {
        val sharedPref = context.getSharedPreferences("Reminders", Context.MODE_PRIVATE)
        val key = "$day-$monthYear"
        
        // સેવ કરેલા રિમાઇન્ડર મેળવવા
        val savedReminder = sharedPref.getString(key, "No custom reminder set") ?: ""

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Reminders: $day $monthYear")
        
        val list = mutableListOf<String>()
        val fest = getFestival(day.toInt(), monthYear)
        if (fest.isNotEmpty()) list.add("⭐ $fest")
        list.add("📝 Custom: $savedReminder")
        list.add("📁 File Income Tax")
        list.add("🛒 Grocery")

        builder.setItems(list.toTypedArray()) { _, _ ->
            showEditDialog(context, day, monthYear)
        }
        builder.setNegativeButton("Close", null)
        builder.show()
    }

    // રિમાઇન્ડર સેવ કરવા માટેનો અસલી ડાયલોગ
    private fun showEditDialog(context: Context, day: String, monthYear: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Set Reminder for $day $monthYear")
        
        val input = EditText(context)
        input.hint = "Enter your task here..."
        builder.setView(input)

        builder.setPositiveButton("SAVE") { _, _ ->
            val text = input.text.toString()
            val sharedPref = context.getSharedPreferences("Reminders", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("$day-$monthYear", text)
                apply() // અહીં ડેટા સેવ થશે
            }
            Toast.makeText(context, "Reminder Saved!", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Cancel", null)
        builder.show()
    }

    override fun getItemCount(): Int = months.size
}
