package com.suatzengin.onlineauction.ui.navigation

sealed class Screen(val route: String) {
    data object Auctions : Screen("auctions")
    data object AuctionDetail : Screen("auction_detail")
}