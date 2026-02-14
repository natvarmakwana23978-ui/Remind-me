fun main() {
    val year = 2026
    val calendar = YearlyCalendar()
    val allMonths = calendar.getFullYear(year)

    println("=====================================")
    println("      ENGLISH CALENDAR - $year       ")
    println("=====================================\n")

    for (month in allMonths) {
        println("---------- ${month.monthName} ----------")
        // તમારી સૂચના મુજબનું નવું હેડર (રવિવારથી શરૂ)
        println("S   M   T   W   T   F   S") 
        
        // રવિવારથી શરૂ થતા કેલેન્ડરમાં ખાલી જગ્યાનું લોજિક
        // જો startDayOfWeek ૧(Mo) હોય તો ૧ ખાલી જગ્યા, ૭(Su) હોય તો ૦.
        val spaces = if (month.startDayOfWeek == 7) 0 else month.startDayOfWeek
        
        for (i in 0 until spaces) {
            print("    ")
        }

        for (day in 1..month.totalDays) {
            // ફક્ત તારીખ પ્રિન્ટ થશે, (S) કાઢી નાખ્યું છે
            print(String.format("%02d  ", day))
            
            // જો અઠવાડિયું પૂરું થાય તો નવી લાઈન
            if ((day + spaces) % 7 == 0) {
                println()
            } else {
                print("  ")
            }
        }
        println("\n") 
    }
}
