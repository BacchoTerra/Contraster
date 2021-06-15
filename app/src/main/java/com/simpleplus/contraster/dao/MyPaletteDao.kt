package com.simpleplus.contraster.dao

import androidx.room.*
import com.simpleplus.contraster.model.MyPalette
import kotlinx.coroutines.flow.Flow

@Dao
interface MyPaletteDao {

    @Insert
    suspend fun insert(palette:MyPalette)

    @Delete
    suspend fun delete(palette:MyPalette)

    @Update
    suspend fun update(palette:MyPalette)

    @Query ("SELECT * FROM palette_table")
    fun selectAll():Flow<List<MyPalette>>

}