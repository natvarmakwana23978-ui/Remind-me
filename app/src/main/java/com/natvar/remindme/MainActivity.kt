package com.natvar.remindme

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import org.json.JSONArray
import java.net.URL

class MainActivity : AppCompatActivity() {

    // તમારી ગૂગલ શીટની 'Web App' લિંક અહીં મૂકવી
    private val sheetUrl = "https://script.google.com/macros/s/AKfycbxgAC_Vg3d7c5P5iyieEmqSGM1AqGjW3tXC36wk1rx4yJR0NuE8_Bc90W0NyTadxcA/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calendarHeader: TextView = findViewById(R.id.calendarHeader)
        val calendarGrid: GridView = findViewById(R.id.calendarGrid)

        calendarHeader.text = "ફેબ્રુઆરી ૨૦૨૬ (લોડ થઈ રહ્યું છે...)"

        // ગૂગલ શીટ્સમાંથી ડેટા લાવવાનું કામ શરૂ કરો
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // શીટ્સમાંથી ડેટા ખેંચવો
                val result = URL(sheetUrl).readText()
                val jsonArray = JSONArray(result)
                val displayList = mutableListOf<String>()

                // ડેટાને લિસ્ટમાં ગોઠવવો
                for (i in 0 until jsonArray.length()) {
                    val obj = jsonArray.getJSONObject(i)
                    val date = obj.getString("date")
                    val event = obj.getString("event")
                    displayList.add("$date - $event")
                }

                // સ્ક્રીન પર ડેટા બતાવવો
                withContext(Dispatchers.Main) {
                    calendarHeader.text = "મારા રિમાઇન્ડર્સ"
                    val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, displayList)
                    calendarGrid.adapter = adapter
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "ડેટા લોડ કરવામાં ભૂલ આવી!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
