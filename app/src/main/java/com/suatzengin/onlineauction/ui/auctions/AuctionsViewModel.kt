package com.suatzengin.onlineauction.ui.auctions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.suatzengin.onlineauction.data.AuctionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuctionsViewModel(
    private val auctionRepository: AuctionRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuctionsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val auctions = auctionRepository.getAuctions()
            _uiState.update { it.copy(auctions = auctions) }
        }
    }

    companion object {
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val auctionRepository = AuctionRepository()
                AuctionsViewModel(auctionRepository)
            }
        }
    }
}
