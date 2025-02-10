package com.example.amphibians.ui.screen

import android.text.Editable.Factory
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.amphibians.AmphibiansApplication
import com.example.amphibians.data.Amphibian
import com.example.amphibians.data.NetworkAmphibiansRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface AmphibiansUiState {
    data class Success(val amphibians: List<Amphibian>): AmphibiansUiState
    class Error: AmphibiansUiState
    class Loading: AmphibiansUiState
}

class AmphibiansViewModel(
    private val amphibiansRepository: NetworkAmphibiansRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<AmphibiansUiState> = MutableStateFlow(AmphibiansUiState.Loading())
    val uiState = _uiState.asStateFlow()

    private fun getAmphibiansData() {
        viewModelScope.launch {
            try {
                val result = amphibiansRepository.getAmphibians()
                Log.d("jerrytet", "getAmphibiansData: $result")
                _uiState.value = AmphibiansUiState.Success(result)
            } catch (e: Exception) {
                _uiState.value = AmphibiansUiState.Error()
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AmphibiansApplication)
                val amphibiansRepository = application.container.amphibiansRepository
                AmphibiansViewModel(amphibiansRepository)
            }
        }
    }

    init {
        getAmphibiansData()
    }
}