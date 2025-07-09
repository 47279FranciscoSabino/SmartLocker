package com.example.smartlockerandroid.data

import com.example.smartlockerandroid.data.service.FriendsService
import com.example.smartlockerandroid.data.service.HistoryService
import com.example.smartlockerandroid.data.service.LockerService
import com.example.smartlockerandroid.data.service.ModuleService
import com.example.smartlockerandroid.data.service.ScanService
import com.example.smartlockerandroid.data.service.TradeService
import com.example.smartlockerandroid.data.service.UserService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:8080/api/"

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val lockerService: LockerService = retrofit.create(LockerService::class.java)
    val userService: UserService = retrofit.create(UserService::class.java)
    val friendsService: FriendsService = retrofit.create(FriendsService::class.java)
    val tradeService: TradeService = retrofit.create(TradeService::class.java)
    val moduleService: ModuleService = retrofit.create(ModuleService::class.java)
    val historyService: HistoryService = retrofit.create(HistoryService::class.java)
    val scanService: ScanService = retrofit.create(ScanService::class.java)
}