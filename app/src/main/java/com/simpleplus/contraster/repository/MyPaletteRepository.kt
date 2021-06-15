package com.simpleplus.contraster.repository

import com.simpleplus.contraster.dao.MyPaletteDao
import com.simpleplus.contraster.model.MyPalette

class MyPaletteRepository (private val dao:MyPaletteDao) {

    val allPalettes = dao.selectAll()

    suspend fun insert(palette: MyPalette) {

        dao.insert(palette)

    }
    suspend fun update(palette: MyPalette) {

        dao.update(palette)

    }

    suspend fun delete(palette: MyPalette) {

        dao.delete(palette)

    }

}