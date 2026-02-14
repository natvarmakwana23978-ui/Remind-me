package com.natvar.remindme

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calendarGrid: GridView = findViewById(R.id.calendarGrid)
        
        // ફેબ્રુઆરી ૨૦૨૬ માટે ૧ થી ૨૮ તારીખો (અંગ્રેજીમાં)
        val dates = Array(28) { (it + 1).toString() }

        val adapter = object : ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dates) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent) as TextView
                
                // દરેક ખાનાની ડિઝાઇન
                view.setBackgroundColor(Color.WHITE)
                view.textSize = 18f
                view.setTextColor(Color.BLACK)
                view.gravity = Gravity.CENTER
                
                // ખાનાની ઊંચાઈ સેટ કરવી (જેથી કેલેન્ડર ચોરસ દેખાય)
                val params = view.layoutParams ?: ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                view.layoutParams = params
                view.height = 150 // પિક્સેલ્સમાં ઊંચાઈ
                
                // રવિવારની તારીખ (1, 8, 15, 22) લાલ રંગમાં
                if (position % 7 == 0) {
                    view.setTextColor(Color.RED)
                }
                
                return view
            }
        }

        calendarGrid.adapter = adapter
    }
}
