package com.example.smartlockerandroid.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.smartlockerandroid.data.RetrofitInstance
import com.example.smartlockerandroid.ui.components.TopBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    onInfoRequest: (() -> Unit)? = null,
    onProfileRequest: (() -> Unit)? = null,
    onHistoryRequest: (() -> Unit) = { },
    onNewRequest: () -> Unit = { }
){
    val tabs = listOf("New Trade", "My Trades")
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { 2 }
    )
    Scaffold(
        topBar = {
            TopBar(
                onInfoRequest = onInfoRequest,
                onProfileRequest = onProfileRequest
            )
        }
    ) {
        Column(Modifier.padding(it)) {
            TabRow(
                selectedTabIndex = pagerState.currentPage
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            CoroutineScope(Dispatchers.Main).launch {
                                pagerState.scrollToPage(index)
                            }
                        },
                        text = { Text(title) }
                    )
                }
            }
            HorizontalPager(state = pagerState) { page ->
                when (page) {
                    0 -> NewTradeScreen(onNewRequest)
                    1 -> MyTradesScreen(
                        service = RetrofitInstance.historyService, // Replace with your actual service instance
                        userId = 1,
                        onClickRequest = onHistoryRequest
                    )
                }
            }


        }
    }
}

@Preview(showBackground  = true)
@Composable
fun MainPreview() {
    MainScreen()
}