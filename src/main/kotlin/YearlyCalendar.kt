/**
 * આખા વર્ષનું કેલેન્ડર જનરેટ કરવા માટેનું લોજિક
 */
class YearlyCalendar {

    private val provider = CalendarProvider()

    // આપેલ વર્ષના ૧ થી ૧૨ મહિનાનો ડેટા આપશે
    fun getFullYear(year: Int): List<MonthDetails> {
        val yearData = mutableListOf<MonthDetails>()
        
        for (month in 1..12) {
            yearData.add(provider.getMonthDetails(year, month))
        }
        
        return yearData
    }

    // કેલેન્ડરનો સારાંશ બતાવવા (Check કરવા માટે)
    fun printYearSummary(year: Int) {
        val yearData = getFullYear(year)
        yearData.forEach { month ->
            println("${month.monthName} ${month.year}: ${month.totalDays} દિવસો, શરૂઆતનો વાર: ${month.startDayOfWeek}")
        }
    }
}

