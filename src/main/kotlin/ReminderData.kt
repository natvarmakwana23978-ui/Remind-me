/**
 * રીમાઇન્ડર સ્ટોર કરવા માટેનું માળખું
 */
data class ReminderItem(
    val date: String,    // "2026-02-14"
    val title: String,   // "શનિ પ્રદોષ પૂજા"
    val category: String // "RELIGIOUS" અથવા "HOUSEHOLD"
)

class ReminderService {
    // આ લિસ્ટ ભવિષ્યમાં ગૂગલ શીટ્સમાંથી આવશે
    private val reminderList = listOf(
        ReminderItem("2026-02-14", "શનિ પ્રદોષ પૂજા", "RELIGIOUS"),
        ReminderItem("2026-02-15", "કરિયાણું લાવવું", "HOUSEHOLD"),
        ReminderItem("2026-03-01", "હોળીની તૈયારી", "RELIGIOUS")
    )

    fun getRemindersForDate(date: String): List<ReminderItem> {
        return reminderList.filter { it.date == date }
    }
}

