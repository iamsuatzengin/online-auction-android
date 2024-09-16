package com.suatzengin.onlineauction.ui.auctiondetail.model

data class RecommendedPrice(
    val value: Int,
    val text: String,
)

fun Int.recommendedPrices(): List<RecommendedPrice> {
    val list = listOf(
        this + 1_000,
        this + 2_000,
        this + 3_000,
        this + 4_000
    )

    return list.map {
        RecommendedPrice(
            value = it,
            text = "$${it / 1000}k"
        )
    }
}
