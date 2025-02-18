package com.assignment.android.api

import com.assignment.android.model.Item
import retrofit2.http.GET

interface ApiService {

    @GET("hiring.json")
    suspend fun getList(): List<Item>?
}