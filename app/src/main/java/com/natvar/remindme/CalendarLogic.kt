import java.time.LocalDate
import java.time.YearMonth

/**
 * Remind Me - સાદું અંગ્રેજી કેલેન્ડર લોજિક
 */
class CalendarLogic {

    // આપેલ વર્ષ અને મહિના માટે તારીખોની યાદી બનાવશે
    fun getDatesInMonth(year: Int, month: Int): List<LocalDate> {
        val yearMonth = YearMonth.of(year, month)
        val daysInMonth = yearMonth.lengthOfMonth()
        val dates = mutableListOf<LocalDate>()

        for (day in 1..daysInMonth) {
            dates.add(yearMonth.atDay(day))
        }
        return dates
    }

    // કેલેન્ડર પ્રિન્ટ કરવા માટેનું ફંક્શન (Testing हेतु)
    fun printMonth(year: Int, month: Int) {
        val dates = getDatesInMonth(year, month)
        println("કેલેન્ડર: $month / $year")
        dates.forEach { println(it.dayOfMonth) }
    }
}

