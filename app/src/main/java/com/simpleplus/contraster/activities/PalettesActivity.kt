package com.simpleplus.contraster.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.simpleplus.contraster.application.ContrasterApplication
import com.simpleplus.contraster.adapter.PaletteAdapter
import com.simpleplus.contraster.databinding.ActivityPalettesBinding
import com.simpleplus.contraster.model.MyPalette
import com.simpleplus.contraster.viewmodel.MyPaletteViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PalettesActivity : AppCompatActivity() {

    companion object {
        const val KEY_FOR_BACKGROUND = "com.simpleplus.dev.BACKGROUND_KEY"
        const val KEY_FOR_FOREGROUND = "com.simpleplus.dev.FOREGROUND_KEY"
    }

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
        initAd()
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

    private fun initAd () {

        lifecycleScope.launch(Dispatchers.Main) {
            val adRequest = AdRequest.Builder().build()
            binder.activityPalettesBannerAdd.loadAd(adRequest)
        }

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

    fun sendPaletteToEdition(backgroundColor:Int,foregroundColor:Int) {

        setResult(RESULT_OK,Intent().putExtra(KEY_FOR_BACKGROUND,backgroundColor).putExtra(
            KEY_FOR_FOREGROUND,foregroundColor))
        finish()

    }
}