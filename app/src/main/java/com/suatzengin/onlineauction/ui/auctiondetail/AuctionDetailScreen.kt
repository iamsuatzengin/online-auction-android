package com.suatzengin.onlineauction.ui.auctiondetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.suatzengin.onlineauction.data.model.Auction
import com.suatzengin.onlineauction.ui.auctiondetail.model.RecommendedPrice
import com.suatzengin.onlineauction.ui.auctiondetail.model.recommendedPrices
import com.suatzengin.onlineauction.ui.ext.noRippleClickable
import com.suatzengin.onlineauction.ui.theme.CardBg
import com.suatzengin.onlineauction.ui.theme.Dark
import com.suatzengin.onlineauction.ui.theme.Green
import com.suatzengin.onlineauction.ui.theme.Orange

@Composable
fun AuctionDetailScreen(
    auction: Auction?,
    viewModel: AuctionDetailViewModel = viewModel(factory = AuctionDetailViewModel.Factory)
) {
    if (auction == null) {
        Text(text = "Auction not found")
        return
    }

    DisposableEffect(Unit) {
        viewModel.connect(auction.id)

        onDispose {
            viewModel.disconnect()
        }
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        AsyncImage(
            model = auction.imageUrl,
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .offset(y = (-24).dp)
                .fillMaxWidth()
                .background(
                    color = Dark,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = auction.title,
                color = Color.White,
                style = MaterialTheme.typography.titleLarge
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .offset(y = (-24).dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(
                        color = CardBg,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Column {
                    Text(
                        text = "Start Price",
                        color = Color(0xFF001B2E),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "$${auction.startPrice}")

                    Text(text = "${uiState.bidderCount} are live", fontSize = 13.sp, color = Green)
                }

                Spacer(modifier = Modifier.width(16.dp))

                VerticalDivider(modifier = Modifier.height(64.dp))

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = "Current Bid Price",
                        color = Color(0xFF001B2E),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(text = "$${uiState.currentPrice}")

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = Orange
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "01:20s remaining", fontSize = 14.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(color = Orange.copy(0.5f), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .background(color = Orange, shape = CircleShape)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Live Auction",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(uiState.offers) { model ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    val buildAnnotatedString = buildAnnotatedString {
                        append(text = "Offer: ")

                        withStyle(
                            style = SpanStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        ) {
                            append("$${model.amount}")
                        }
                    }

                    Text(
                        text = buildAnnotatedString,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(text = "01/09/2024 21:47")
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        BidRow(
            modifier = Modifier.padding(horizontal = 16.dp),
            currentPrice = uiState.currentPrice,
            onCustomBidClick = { },
            onBidClick = { amount ->
                viewModel.sendMessage(auctionId = auction.id, amount = amount)
            }
        )
    }
}

@Composable
private fun BidRow(
    currentPrice: Int,
    onCustomBidClick: () -> Unit,
    onBidClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val recommendedPrices = remember(currentPrice) {
        currentPrice.recommendedPrices()
    }

    var selectedPrice by remember { mutableStateOf(RecommendedPrice(0, "")) }

    Column(
        modifier = modifier
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            items(recommendedPrices) { recommendedPrice ->
                Text(
                    text = recommendedPrice.text,
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = if (selectedPrice == recommendedPrice) Color.Black else Color.LightGray,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                        .noRippleClickable {
                            selectedPrice = recommendedPrice
                        }
                )
            }

            item {
                Text(
                    text = "Use custom bid",
                    color = Color.White,
                    modifier = Modifier
                        .background(
                            color = Color.Black,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(4.dp)
                        .clickable(onClick = onCustomBidClick)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { onBidClick(selectedPrice.value) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Orange
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
        ) {
            Text(text = "Place Bid for ${selectedPrice.text}", color = Dark)
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun BidRowPreview() {
    BidRow(
        currentPrice = 200,
        onCustomBidClick = {},
        onBidClick = {}
    )
}
