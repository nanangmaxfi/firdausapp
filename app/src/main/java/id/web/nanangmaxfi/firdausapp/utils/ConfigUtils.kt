package id.web.nanangmaxfi.firdausapp.utils

import java.text.SimpleDateFormat
import java.util.*

class ConfigUtils {
    companion object{
        @Volatile
        private var instance: ConfigUtils? = null

        fun getInstance(): ConfigUtils =
                instance ?: synchronized(this){
                    instance ?: ConfigUtils()
                }
    }

    fun getCurrentDate(): String{
        val format = SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH)
        return format.format(Date())
    }

    fun getCurrentYear(): String{
        val format = SimpleDateFormat("yyyy", Locale.ENGLISH)
        return format.format(Date())
    }

    fun getCurrentMonth(): String{
        val format = SimpleDateFormat("MM", Locale.ENGLISH)
        return format.format(Date())
    }

    fun getCurrentDateNumb(): String{
        val format = SimpleDateFormat("dd", Locale.ENGLISH)
        return format.format(Date())
    }

    fun getTomorrowDate(): String{
        val format = SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH)
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.DATE, 1)
        return format.format(calendar.time)
    }

    fun getTomorrowDate(format: String): String{
        val format = SimpleDateFormat(format, Locale.ENGLISH)
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.DATE, 1)
        return format.format(calendar.time)
    }

    fun convertTimeToMilis(time: String?): Long{
        val format = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.ENGLISH)
        val date = format.parse("${getCurrentDate()} $time")
        return date?.time?:Date().time
    }

    fun convertTimeToMilisByDate(date: String, time: String?): Long{
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
        val date = format.parse("$date $time")
        return date?.time?:Date().time
    }

    fun diffTimeLong(time: Long?): Long{
        val now = Date()
        return (time ?: now.time) - now.time
    }
}