package com.natvar.remindme

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SelectionActivity : AppCompatActivity() {

    // તમારી ગૂગલ શીટ મુજબના ૨૭ કેલેન્ડરની યાદી [cite: 2026-02-07]
    private val calendarTypes = arrayOf(
        "ENGLISH", "ગુજરાતી (Gujarati)", "हिन्दी (Hindi)", "ઇસ્લામિક (Islamic)",
        "తెలుగు/ಕನ್ನಡ (Telugu/Kannada)", "தமிழ் (Tamil)", "മലയാളം (Malayalam)",
        "ਪੰਜਾਬੀ (Punjabi)", "ଓଡ଼ିଆ (Odia)", "বাংলা (Bengali)", "नेपाली (Nepali)",
        "中文 (Chinese)", "עברית (Hebrew)", "فارસી (Persian)", "ኢትዮጵያ (Ethiopian)",
        "Basa Bali (Balinese)", "한국어 (Korean)", "Tiếng Việt (Vietnamese)",
        "ไทย (Thai)", "Français (French)", "မြန်မာဘာသာ (Burmese)",
        "کاشمیری (Kashmiri)", "મારવાડી (Marwari)", "日本語 (Japanese)",
        "অসমীয়া (Assamese)", "سنڌી (Sindhi)", "བོད་སྐད (Tibetan)",
        "➕ Create New Calendar" // તમારું પગલું ૩ [cite: 2026-02-14]
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // ડાયનેમિક લિસ્ટ વ્યૂ બનાવવો
        val listView = ListView(this)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, calendarTypes)
        listView.adapter = adapter

        // હેડર સેટ કરવું
        val header = TextView(this)
        header.text = "Select Your Calendar System"
        header.textSize = 22f
        header.setPadding(40, 40, 40, 40)
        header.setTextColor(resources.getColor(R.color.purple_700))
        listView.addHeaderView(header)

        setContentView(listView)

        listView.setOnItemClickListener { _, _, position, _ ->
            // હેડરને કારણે પોઝિશન ૧ થી શરૂ થશે
            if (position > 0) {
                val selected = calendarTypes[position - 1]
                
                if (selected == "➕ Create New Calendar") {
                    // નવું કેલેન્ડર બનાવવાનું ફોર્મ ખોલવું (પગલું ૪)
                    Toast.makeText(this, "Opening Calendar Builder...", Toast.LENGTH_SHORT).show()
                } else {
                    // પસંદગી સેવ કરવી
                    saveSelection(selected)
                    // મુખ્ય કેલેન્ડર સ્ક્રીન પર જવું
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }
    }

    private fun saveSelection(calendarName: String) {
        val sharedPref = getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("selected_calendar", calendarName)
            apply()
        }
    }
}
