package com.simpleplus.contraster.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.simpleplus.contraster.ContrasterApplication
import com.simpleplus.contraster.R
import com.simpleplus.contraster.adapter.PaletteAdapter
import com.simpleplus.contraster.databinding.ActivityPalettesBinding
import com.simpleplus.contraster.viewmodel.MyPaletteViewModel

class PalettesActivity : AppCompatActivity() {

    //Layout components
    private val binder by lazy {
        ActivityPalettesBinding.inflate(layoutInflater)
    }

    //ViewModel
    private val viewModel : MyPaletteViewModel by viewModels {
        MyPaletteViewModel.MyPaletteViewModelFactory((application as ContrasterApplication).repository)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binder.root)
        initRecyclerView()
        initToolbar()
    }

    private fun initToolbar() {

        setSupportActionBar(binder.activityPalettesToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun initRecyclerView() {

        val adapter = PaletteAdapter(this@PalettesActivity,viewModel,layoutInflater)

        binder.activityPalettesRecyclerView.apply {

            layoutManager = LinearLayoutManager(this@PalettesActivity)
            this.adapter = adapter
            setHasFixedSize(true)
        }

        retrieveAllPalettes(adapter)


    }

    private fun retrieveAllPalettes(adapter: PaletteAdapter) {

        viewModel.allPalettes.observe(this) {

            if(it.isEmpty()) {
                binder.activityPalettesContentNoContent.root.visibility = View.VISIBLE
            }else{
                binder.activityPalettesContentNoContent.root.visibility = View.GONE
            }

            adapter.submitList(it)

        }

    }
}