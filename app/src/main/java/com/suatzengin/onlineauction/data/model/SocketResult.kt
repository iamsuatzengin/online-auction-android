package com.suatzengin.onlineauction.data.model

sealed interface SocketResult {
    data class Success(
        val offers: List<SocketApiModel>,
        val bidderCount: Int,
        val currentPrice: Int,
        val isOfferApproved: Boolean = false,
        val warningMessage: String = ""
    ) : SocketResult
    data class Error(val message: String) : SocketResult
}
