package com.example.smartlockerandroid.data.service

import com.example.smartlockerandroid.data.model.module.ModuleDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ModuleService {
    @GET("module/{id}")
    suspend fun getModule(@Path("id") id: Int):ModuleDTO

    @GET("geo/{latitude}/{longitude}")
    suspend fun getModulesByRadius(@Path("latitude") latitude: Double, @Path("longitude") longitude: Double, @Query("radius") radius: Double): List<ModuleDTO>

}