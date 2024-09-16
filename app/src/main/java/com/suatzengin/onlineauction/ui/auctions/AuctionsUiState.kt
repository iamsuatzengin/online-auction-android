package com.suatzengin.onlineauction.ui.auctions

import com.suatzengin.onlineauction.data.model.Auction

data class AuctionsUiState(
    val auctions: List<Auction> = emptyList(),
    val isLoading: Boolean = false
)
