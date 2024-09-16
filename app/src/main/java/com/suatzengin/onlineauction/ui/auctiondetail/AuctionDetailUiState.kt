package com.suatzengin.onlineauction.ui.auctiondetail

import com.suatzengin.onlineauction.data.model.SocketApiModel

data class AuctionDetailUiState(
    val offers: List<SocketApiModel> = emptyList(),
    val bidderCount: Int = 0,
    val currentPrice: Int = 0,
)
