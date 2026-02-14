import java.time.LocalDate
import java.time.DayOfWeek

/**
 * કેલેન્ડર માટે વધારાની ઉપયોગી સુવિધાઓ
 */
object CalendarUtils {

    // શું આપેલ તારીખ રવિવાર છે?
    fun isSunday(date: LocalDate): Boolean {
        return date.dayOfWeek == DayOfWeek.SUNDAY
    }

    // શું આપેલ તારીખ શનિવાર છે?
    fun isSaturday(date: LocalDate): Boolean {
        return date.dayOfWeek == DayOfWeek.SATURDAY
    }

    // તારીખને વાંચવા યોગ્ય ફોર્મેટમાં ફેરવો (દા.ત. 14 Feb 2026)
    fun getFormattedDate(date: LocalDate): String {
        return "${date.dayOfMonth} ${date.month.name.lowercase().capitalize()} ${date.year}"
    }
}

