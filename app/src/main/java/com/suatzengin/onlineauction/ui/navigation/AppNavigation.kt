package com.suatzengin.onlineauction.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.suatzengin.onlineauction.data.model.Auction
import com.suatzengin.onlineauction.ui.auctiondetail.AuctionDetailScreen
import com.suatzengin.onlineauction.ui.auctions.AuctionsScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Auctions.route,
        modifier = modifier
    ) {
        composable(route = Screen.Auctions.route) {
            AuctionsScreen(
                onAuctionItemClick = { auction ->
                    navController.currentBackStackEntry?.savedStateHandle?.set(key = "auction", value = auction)

                    navController.navigate(Screen.AuctionDetail.route)
                }
            )
        }

        composable(
            route = Screen.AuctionDetail.route
        ) {
            val auction = navController.previousBackStackEntry?.savedStateHandle?.get<Auction>("auction")

            AuctionDetailScreen(auction)
        }
    }
}
