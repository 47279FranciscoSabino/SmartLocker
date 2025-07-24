package com.example.smartlockerandroid.ui.screens.mainscreen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartlockerandroid.R
import com.example.smartlockerandroid.TokenProvider
import com.example.smartlockerandroid.data.service.HistoryService
import com.example.smartlockerandroid.data.service.ModuleService
import com.example.smartlockerandroid.data.service.TradeService
import com.example.smartlockerandroid.ui.components.TopBar
import com.example.smartlockerandroid.utils.viewModelInit
import com.example.smartlockerandroid.viewmodel.MyTradesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    onProfileRequest: (() -> Unit)? = null,
    onHistoryRequest: () -> Unit = { },
    onTradeInfoRequest: (Int) -> Unit = {},
    onNewRequest: () -> Unit = { },
    moduleService: ModuleService,
    tradeService: TradeService,
    historyService: HistoryService,

    ){
    val context = LocalContext.current
    val token = remember { mutableStateOf<String?>(null) }
    token.value = TokenProvider.getToken(context)

    val myTradesViewModel: MyTradesViewModel = viewModel(
        factory = viewModelInit { MyTradesViewModel(tradeService, historyService, token.value!!) }
    )
    val received = myTradesViewModel.pendingTrades


    val tabs = listOf(stringResource(R.string.new_trade), stringResource(R.string.my_trades))
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { 2 }
    )

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(received) {
        var count = 0
        if (received.isNotEmpty()) {
            for (trade in received) {
                if(!trade.read) count++
            }
            if(count >0) snackbarHostState.showSnackbar("You have $count trades to withdraw.")
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopBar(
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
                    0 -> NewTradeScreen(
                        onClickRequest = onNewRequest,
                        moduleService = moduleService,
                    )
                    1 -> MyTradesScreen(
                        onClickRequest = onTradeInfoRequest,
                        onHistoryRequest = onHistoryRequest,
                        viewModel = myTradesViewModel
                    )
                }
            }
        }
    }
}
