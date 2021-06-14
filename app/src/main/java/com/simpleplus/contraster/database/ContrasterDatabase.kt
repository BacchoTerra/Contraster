package com.simpleplus.contraster.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.simpleplus.contraster.dao.MyPaletteDao
import com.simpleplus.contraster.model.MyPalette

@Database(entities = [MyPalette::class], version = 1)
abstract class ContrasterDatabase : RoomDatabase() {

    abstract fun getDao(): MyPaletteDao

    companion object {

        private var INSTANCE: ContrasterDatabase? = null


        fun getInstance(application: Application): ContrasterDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    application,
                    ContrasterDatabase::class.java,
                    "contraster_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance

                instance
            }
        }
    }

}