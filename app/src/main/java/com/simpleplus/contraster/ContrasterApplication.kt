package com.simpleplus.contraster

import android.app.Application
import com.simpleplus.contraster.database.ContrasterDatabase
import com.simpleplus.contraster.repository.MyPaletteRepository

class ContrasterApplication : Application() {


    val database by lazy { ContrasterDatabase.getInstance(this) }

    val repository by lazy { MyPaletteRepository(database.getDao()) }

}