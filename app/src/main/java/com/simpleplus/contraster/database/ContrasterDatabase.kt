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
                ).addCallback(ContrasterDatabaseCallback(scope))
                    .build()
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
                    val paletteDao = it.getDao()

                    paletteDao.deleteAll()

                    paletteDao.apply {
                        insert(MyPalette("Our company", "AA", -1778689, -14409480))
                        insert(MyPalette("Natural", "AA", -132353, -14459389))
                        insert(MyPalette("Woods", "AA", -8371200, -2823215))
                        insert(MyPalette("Aggressive red", "AA", -13762560, -64251))
                        insert(MyPalette("Publicity", "AA", -11599704, -2374400))
                        insert(MyPalette("Modern", "AA", -16777170, -2721536))
                    }


                }
            }
        }

    }

}