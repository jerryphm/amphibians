package com.example.amphibians.ui.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

class AmphibiansViewModel: ViewModel() {
    private val _uiState: MutableStateFlow<AmphibiansUiState> = MutableStateFlow(AmphibiansUiState.Loading())
    val uiState = _uiState.asStateFlow()

    private fun getAmphibiansData() {
        viewModelScope.launch {
            try {
                val result = NetworkAmphibiansRepository().getAmphibians()
                Log.d("jerrytet", "getAmphibiansData: $result")
                _uiState.value = AmphibiansUiState.Success(result)
            } catch (e: Exception) {
                _uiState.value = AmphibiansUiState.Error()
            }
        }
    }

    init {
        getAmphibiansData()
    }
}