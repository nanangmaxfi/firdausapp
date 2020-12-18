package id.web.nanangmaxfi.firdausapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import id.web.nanangmaxfi.firdausapp.R
import id.web.nanangmaxfi.firdausapp.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        supportActionBar?.hide()
        activityMainBinding.textCity.text = "Bantul, Yogyalarta"
        viewModel.getPrayerSchedule("779",getDateNow()).observe(this, {data ->
            activityMainBinding.textDatetime.text = data.tanggal

            activityMainBinding.textTimeSubuh.text = data.subuh
            activityMainBinding.textTimeFajar.text = data.terbit
            activityMainBinding.textTimeDzuhur.text = data.dzuhur
            activityMainBinding.textTimeAshar.text = data.ashar
            activityMainBinding.textTimeMaghrib.text = data.maghrib
            activityMainBinding.textTimeIsya.text = data.isya
        })
    }

    private fun getDateNow(): String{
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        return format.format(Date())
    }
}