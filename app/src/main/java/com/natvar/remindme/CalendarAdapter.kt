package com.natvar.remindme

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
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
                tv.height = 120
                tv.gravity = Gravity.CENTER
                tv.textSize = 18f
                tv.typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)

                if (days[p].isNotEmpty()) {
                    val dayNum = days[p].toInt()
                    val isHoliday = getFestival(dayNum, monthFormat.format(calendar.time)).isNotEmpty()
                    
                    // ૧. રવિવાર (Column 0) અને બીજો-ચોથો શનિવાર (Column 6) લાલ રંગમાં
                    val isSunday = (p % 7 == 0)
                    val isSaturday = (p % 7 == 6)
                    val isSecondSat = isSaturday && (dayNum in 8..14)
                    val isFourthSat = isSaturday && (dayNum in 22..28)

                    if (isSunday || isSecondSat || isFourthSat) {
                        tv.setTextColor(Color.parseColor("#D32F2F")) // Red
                        tv.setTypeface(null, Typeface.BOLD)
                    } else {
                        tv.setTextColor(Color.parseColor("#212121"))
                    }

                    // ૨. તહેવાર વાળી તારીખ માટે બેકગ્રાઉન્ડ હાઈલાઈટ
                    if (isHoliday) {
                        val shape = GradientDrawable()
                        shape.cornerRadius = 15f
                        shape.setColor(Color.parseColor("#FFF9C4")) // Light Gold
                        tv.background = shape
                        tv.setTextColor(Color.parseColor("#FF6F00")) // Orange-ish Text
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
            "January 2026" -> when(day) {
                1 -> "New Year's Day"
                14 -> "Uttarayan"
                26 -> "Republic Day"
                else -> ""
            }
            "February 2026" -> when(day) {
                14 -> "Valentine's Day"
                15 -> "Maha Shivratri"
                else -> ""
            }
            "March 2026" -> when(day) {
                4 -> "Holi"
                20 -> "Eid-ul-Fitr"
                27 -> "Ram Navami"
                else -> ""
            }
            "April 2026" -> when(day) {
                3 -> "Good Friday"
                14 -> "Ambedkar Jayanti"
                else -> ""
            }
            "August 2026" -> when(day) {
                15 -> "Independence Day"
                28 -> "Raksha Bandhan"
                else -> ""
            }
            "September 2026" -> when(day) {
                4 -> "Janmashtami"
                14 -> "Ganesh Chaturthi"
                else -> ""
            }
            "October 2026" -> when(day) {
                2 -> "Gandhi Jayanti"
                20 -> "Dussehra"
                else -> ""
            }
            "November 2026" -> when(day) {
                8 -> "Diwali"
                9 -> "Gujarati New Year"
                10 -> "Bhai Dooj"
                else -> ""
            }
            "December 2026" -> when(day) {
                25 -> "Christmas"
                else -> ""
            }
            else -> ""
        }
    }

    private fun showReminderDialog(context: android.content.Context, day: String, monthYear: String) {
        val reminders = mutableListOf<String>()
        val festival = getFestival(day.toInt(), monthYear)
        
        if (festival.isNotEmpty()) reminders.add("⭐ $festival")
        
        reminders.add("📁 File Income Tax Return")
        reminders.add("🛒 Purchase Grocery")
        reminders.add("🏥 Hospital Health Checkup")
        reminders.add("🎂 Happy Birthday")

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Reminders: $day $monthYear")
        builder.setMessage("• " + reminders.joinToString("\n\n• "))
        builder.setPositiveButton("OK") { d, _ -> d.dismiss() }
        builder.show()
    }

    override fun getItemCount(): Int = months.size
}
