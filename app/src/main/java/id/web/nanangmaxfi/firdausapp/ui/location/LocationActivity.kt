package id.web.nanangmaxfi.firdausapp.ui.location

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupieAdapter
import id.web.nanangmaxfi.firdausapp.data.source.local.entity.LocationEntity
import id.web.nanangmaxfi.firdausapp.databinding.ActivityLocationBinding
import id.web.nanangmaxfi.firdausapp.viewmodel.ViewModelFactory
import id.web.nanangmaxfi.firdausapp.vo.Status

class LocationActivity : AppCompatActivity() {
    companion object{
        private var TAG = this::class.java.simpleName
    }

    private lateinit var binding: ActivityLocationBinding
    private lateinit var viewModel: LocationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_location)
        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[LocationViewModel::class.java]

        //binding.inputLocation.addTextChangedListener(textWatcher)
        binding.btnSearch.setOnClickListener {
            val inputName = binding.inputLocation.text.toString()
            searchLocation(inputName)
        }
    }

    private val textWatcher = object : TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun afterTextChanged(p0: Editable?) {
            Log.i(TAG,"TEXT: "+p0.toString())
            if (p0 != null) {
                if (p0.isNotEmpty()){
                    Log.i(TAG,"TEXT NOT EMPTY: "+p0.toString())
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
                adapter.add(LocationItem(location))
            }
        }
        binding.rvLocation.layoutManager = LinearLayoutManager(this)
        binding.rvLocation.adapter = adapter
    }
}