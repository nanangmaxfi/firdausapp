package id.web.nanangmaxfi.firdausapp.ui.location

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupieAdapter
import id.web.nanangmaxfi.firdausapp.data.source.local.entity.LocationEntity
import id.web.nanangmaxfi.firdausapp.data.source.preference.LocationModel
import id.web.nanangmaxfi.firdausapp.data.source.preference.LocationPreference
import id.web.nanangmaxfi.firdausapp.databinding.ActivityLocationBinding
import id.web.nanangmaxfi.firdausapp.viewmodel.ViewModelFactory
import id.web.nanangmaxfi.firdausapp.vo.Status

class LocationActivity : AppCompatActivity() {
    companion object{
        private var TAG = this::class.java.simpleName
    }

    private lateinit var binding: ActivityLocationBinding
    private lateinit var viewModel: LocationViewModel
    private lateinit var locationPreference: LocationPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_location)
        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[LocationViewModel::class.java]
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        locationPreference = LocationPreference(this)

        binding.inputLocation.addTextChangedListener(textWatcher)

    }

    private val textWatcher = object : TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun afterTextChanged(p0: Editable?) {
            if (p0 != null) {
                if (p0.isNotEmpty()){
                    searchLocation(p0.toString())
                }
            }
        }

    }

    private fun searchLocation(name: String){
        viewModel.getSearchLocation(name).observe(this){location ->
            Log.i(TAG, "Get Location: "+location.data)
            if (location != null){
                when (location.status) {
                    Status.LOADING -> {
                    }
                    Status.SUCCESS -> {
                        showListLocation(location.data)
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun showListLocation(listLocation: List<LocationEntity>?){
        val adapter = GroupieAdapter()

        if (listLocation != null) {
            for(location in listLocation){
                Log.i(TAG, "Location: "+location.name)
                adapter.add(LocationItem(location, object : LocationItem.OnItemClickListerner{
                    override fun onItemClickListener(location: LocationEntity) {
                        val locationModel = LocationModel()
                        locationModel.cityCode = location.cityCode
                        locationModel.name = location.name
                        locationPreference.setLocation(locationModel)

                        Toast.makeText(applicationContext, "Lokasi berhasil diubah", Toast.LENGTH_LONG).show()
                        finish()
                    }

                }))
            }
        }
        binding.rvLocation.layoutManager = LinearLayoutManager(this)
        binding.rvLocation.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->{
                this.finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}