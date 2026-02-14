import java.time.YearMonth
import java.time.DayOfWeek

/**
 * સાદા અંગ્રેજી કેલેન્ડર માટે મહિનાનું મોડેલ
 */
data class MonthDetails(
    val monthName: String,
    val year: Int,
    val totalDays: Int,
    val startDayOfWeek: Int // ૧ = સોમવાર, ૭ = રવિવાર
)

class CalendarProvider {

    fun getMonthDetails(year: Int, month: Int): MonthDetails {
        val yearMonth = YearMonth.of(year, month)
        
        return MonthDetails(
            monthName = yearMonth.month.name,
            year = year,
            totalDays = yearMonth.lengthOfMonth(),
            // મહિનાની પહેલી તારીખનો વાર મેળવવા
            startDayOfWeek = yearMonth.atDay(1).dayOfWeek.value
        )
    }
}

