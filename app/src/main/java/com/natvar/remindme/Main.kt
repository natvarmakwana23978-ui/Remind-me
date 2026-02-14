fun main() {
    val year = 2026
    val calendar = YearlyCalendar()
    val allMonths = calendar.getFullYear(year)
    
    // નમૂના માટેના રીમાઇન્ડર્સ (આ ડેટા પછીથી શીટ્સમાંથી આવશે)
    val reminders = mapOf(
        "2026-01-14" to "ઉતરાયણ",
        "2026-02-14" to "શનિ પ્રદોષ પૂજા",
        "2026-03-01" to "હોળી"
    )

    println("=====================================")
    println("      REMIND ME CALENDAR - $year      ")
    println("=====================================\n")

    for (month in allMonths) {
        println("---------- ${month.monthName} ----------")
        println("S   M   T   W   T   F   S") 
        
        val spaces = if (month.startDayOfWeek == 7) 0 else month.startDayOfWeek
        
        for (i in 0 until spaces) {
            print("    ")
        }

        for (day in 1..month.totalDays) {
            val dateKey = String.format("%d-%02d-%02d", year, allMonths.indexOf(month) + 1, day)
            
            // જો રીમાઇન્ડર હોય તો તારીખની બાજુમાં '*' મૂકવો
            if (reminders.containsKey(dateKey)) {
                print(String.format("%02d* ", day))
            } else {
                print(String.format("%02d  ", day))
            }
            
            if ((day + spaces) % 7 == 0) {
                println()
            } else {
                print("  ")
            }
        }
        
        // મહિનાના અંતે તે મહિનાના રીમાઇન્ડરનું લિસ્ટ બતાવવું
        println("\n\n--- આ મહિનાના રીમાઇન્ડર ---")
        reminders.forEach { (date, title) ->
            if (date.startsWith(String.format("%d-%02d", year, allMonths.indexOf(month) + 1))) {
                println("• $date: $title")
            }
        }
        println("\n") 
    }
}
