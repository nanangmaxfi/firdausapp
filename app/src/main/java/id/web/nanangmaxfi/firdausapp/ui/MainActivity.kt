package id.web.nanangmaxfi.firdausapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import id.web.nanangmaxfi.firdausapp.R
import id.web.nanangmaxfi.firdausapp.data.source.local.entity.JadwalSholatEntity
import id.web.nanangmaxfi.firdausapp.databinding.ActivityMainBinding
import id.web.nanangmaxfi.firdausapp.viewmodel.ViewModelFactory
import id.web.nanangmaxfi.firdausapp.vo.Status
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        supportActionBar?.hide()
        activityMainBinding.textCity.text = "Bantul, Yogyalarta"
        viewModel.getPrayerSchedule("779",getDateNow()).observe(this, {jadwal ->
            if (jadwal != null){
                when(jadwal.status){
                    Status.LOADING ->{}
                    Status.SUCCESS ->{
                        showPrayerSchedule(jadwal.data)
                    }
                    Status.ERROR ->{
                        Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_LONG).show()
                    }
                }
            }
//            activityMainBinding.textDatetime.text = data.tanggal
//
//            activityMainBinding.textTimeSubuh.text = data.subuh
//            activityMainBinding.textTimeFajar.text = data.terbit
//            activityMainBinding.textTimeDzuhur.text = data.dzuhur
//            activityMainBinding.textTimeAshar.text = data.ashar
//            activityMainBinding.textTimeMaghrib.text = data.maghrib
//            activityMainBinding.textTimeIsya.text = data.isya
        })
    }

    private fun showPrayerSchedule(data: JadwalSholatEntity?){
        activityMainBinding.textDatetime.text = data?.tanggal
        activityMainBinding.textTimeSubuh.text = data?.subuh
        activityMainBinding.textTimeFajar.text = data?.terbit
        activityMainBinding.textTimeDzuhur.text = data?.dzuhur
        activityMainBinding.textTimeAshar.text = data?.ashar
        activityMainBinding.textTimeMaghrib.text = data?.maghrib
        activityMainBinding.textTimeIsya.text = data?.isya
    }

    private fun getDateNow(): String{
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        return format.format(Date())
    }
}