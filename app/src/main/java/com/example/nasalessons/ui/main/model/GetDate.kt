package com.example.nasalessons.ui.main.model

import java.text.SimpleDateFormat
import java.util.*

class GetDate {

    public fun getDate(shiftDate: Int): String {

        // получаю нужный формат прошедшей даты
        fun daysBeforeYesterday(): Date {
            val cal: Calendar = Calendar.getInstance()
            cal.add(Calendar.DATE, -shiftDate)
            return cal.time
        }
        return SimpleDateFormat("yyyy-M-dd").format(daysBeforeYesterday())
    }

}