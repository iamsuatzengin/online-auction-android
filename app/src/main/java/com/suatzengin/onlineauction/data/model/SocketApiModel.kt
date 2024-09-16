package com.suatzengin.onlineauction.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SocketApiModel(
    @SerialName("offerId")
    val offerId: Int? = null,
    @SerialName("auctionId")
    val auctionId: Int? = null,
    @SerialName("amount")
    val amount: Int? = null,
    @SerialName("currentPrice")
    val currentPrice: Int? = null,
    @SerialName("timestamp")
    val timestamp: String? = null,
    @SerialName("isOfferApproved")
    val isOfferApproved: Boolean? = null,
    @SerialName("warningMessage")
    val warningMessage: String? = null,
    @SerialName("bidderCount")
    val bidderCount: Int? = null,
)
