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
                    val fest = getFestival(dayNum, monthFormat.format(calendar.time))
                    
                    // ૨-૪ શનિવાર અને રવિવાર લાલ રંગમાં
                    val isSun = (p % 7 == 0)
                    val isSat = (p % 7 == 6)
                    if (isSun || (isSat && (dayNum in 8..14 || dayNum in 22..28))) {
                        tv.setTextColor(Color.parseColor("#D32F2F"))
                    }

                    // તહેવાર માટે બેકગ્રાઉન્ડ
                    if (fest.isNotEmpty()) {
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

    private fun getFestival(day: Int, monthYear: String): String {
        return when (monthYear) {
            "January 2026" -> when(day) { 1 -> "New Year"; 14 -> "Uttarayan"; 26 -> "Republic Day"; else -> "" }
            "February 2026" -> when(day) { 14 -> "Valentine's Day"; 15 -> "Maha Shivratri"; else -> "" }
            else -> ""
        }
    }

    private fun showReminderDialog(context: Context, day: String, monthYear: String) {
        val key = "$day-$monthYear"
        val sharedPref = context.getSharedPreferences("Reminders", Context.MODE_PRIVATE)
        val savedNotes = sharedPref.getStringSet(key, mutableSetOf())?.toMutableList() ?: mutableListOf()

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Reminders: $day $monthYear")

        val displayList = mutableListOf<String>()
        val fest = getFestival(day.toInt(), monthYear)
        if (fest.isNotEmpty()) displayList.add("⭐ $fest")
        displayList.addAll(savedNotes)

        val adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, displayList)
        builder.setAdapter(adapter) { _, which ->
            // ક્લિક કરવા પર એડિટ/રિમૂવ ઓપ્શન
            showActionDialog(context, key, displayList[which], which)
        }

        builder.setPositiveButton("Add New") { _, _ -> showAddDialog(context, key) }
        builder.setNegativeButton("Close", null)
        builder.show()
    }

    private fun showAddDialog(context: Context, key: String) {
        val input = EditText(context)
        input.hint = "નવું રિમાઇન્ડર લખો..."
        AlertDialog.Builder(context).setTitle("Add Reminder").setView(input)
            .setPositiveButton("Save") { _, _ ->
                val sharedPref = context.getSharedPreferences("Reminders", Context.MODE_PRIVATE)
                val currentSet = sharedPref.getStringSet(key, mutableSetOf())?.toMutableSet() ?: mutableSetOf()
                currentSet.add(input.text.toString())
                sharedPref.edit().putStringSet(key, currentSet).apply()
                Toast.makeText(context, "સેવ થઈ ગયું!", Toast.LENGTH_SHORT).show()
            }.show()
    }

    private fun showActionDialog(context: Context, key: String, content: String, index: Int) {
        val options = arrayOf("Delete Reminder", "Set Notification Time")
        AlertDialog.Builder(context).setTitle("Options: $content").setItems(options) { _, which ->
            if (which == 0) {
                val sharedPref = context.getSharedPreferences("Reminders", Context.MODE_PRIVATE)
                val currentSet = sharedPref.getStringSet(key, mutableSetOf())?.toMutableSet() ?: mutableSetOf()
                currentSet.remove(content)
                sharedPref.edit().putStringSet(key, currentSet).apply()
                Toast.makeText(context, "ડીલીટ થઈ ગયું!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Timer સેટિંગ શરૂ કરો...", Toast.LENGTH_SHORT).show()
            }
        }.show()
    }

    override fun getItemCount(): Int = months.size
}
