package com.simpleplus.contraster.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.simpleplus.contraster.model.MyPalette
import com.simpleplus.contraster.repository.MyPaletteRepository
import kotlinx.coroutines.launch

class MyPaletteViewModel(private val repo:MyPaletteRepository):ViewModel() {

    val allPalettes = repo.allPalettes.asLiveData()

    fun insert(palette:MyPalette) = viewModelScope.launch {

        repo.insert(palette)

    }

    fun update(palette:MyPalette) = viewModelScope.launch {

        repo.update(palette)

    }

    fun delete(palette:MyPalette) = viewModelScope.launch {

        repo.delete(palette)

    }


    class MyPaletteViewModelFactory (private val repo: MyPaletteRepository) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MyPaletteViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MyPaletteViewModel(repo) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }

}