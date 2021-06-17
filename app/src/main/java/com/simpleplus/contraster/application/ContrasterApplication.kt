package com.simpleplus.contraster.application

import android.app.Application
import android.content.Context
import com.google.android.gms.ads.MobileAds
import com.simpleplus.contraster.database.ContrasterDatabase
import com.simpleplus.contraster.repository.MyPaletteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ContrasterApplication : Application() {


    val database by lazy { ContrasterDatabase.getInstance(this, CoroutineScope(SupervisorJob())) }

    val repository by lazy { MyPaletteRepository(database.getDao()) }

    override fun onCreate() {
        super.onCreate()

        MobileAds.initialize(this) {}
    }

}