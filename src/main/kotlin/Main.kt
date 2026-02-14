fun main() {
    val year = 2026
    val calendar = YearlyCalendar()
    val allMonths = calendar.getFullYear(year)

    println("=====================================")
    println("      ENGLISH CALENDAR - $year       ")
    println("=====================================\n")

    for (month in allMonths) {
        println("---------- ${month.monthName} ----------")
        println("Mo  Tu  We  Th  Fr  Sa  Su") // વારના નામ
        
        // મહિનાની શરૂઆત પહેલા ખાલી જગ્યા માટે
        for (i in 1 until month.startDayOfWeek) {
            print("    ")
        }

        for (day in 1..month.totalDays) {
            // રવિવાર હોય તો તારીખની બાજુમાં (S) બતાવશે
            val isSun = (day + month.startDayOfWeek - 1) % 7 == 0
            if (isSun) {
                print(String.format("%02d(S)", day))
            } else {
                print(String.format("%02d  ", day))
            }
            
            if (isSun) println() else print("  ")
        }
        println("\n") 
    }
}
