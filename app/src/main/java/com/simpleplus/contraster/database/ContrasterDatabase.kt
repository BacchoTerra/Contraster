package com.simpleplus.contraster.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.simpleplus.contraster.dao.MyPaletteDao
import com.simpleplus.contraster.model.MyPalette
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [MyPalette::class], version = 1)
abstract class ContrasterDatabase : RoomDatabase() {

    abstract fun getDao(): MyPaletteDao

    companion object {

        private var INSTANCE: ContrasterDatabase? = null


        fun getInstance(application: Application, scope: CoroutineScope): ContrasterDatabase {

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

    private class ContrasterDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let {
                scope.launch {
                    var paletteDao = it.getDao()



                    //TODO: Adicionar pré palettas




                }
            }
        }

    }

}