package com.assignment.android.data

import com.assignment.android.api.ApiService
import com.assignment.android.domain.ListRepository
import com.assignment.android.model.Item

class ListRepositoryImpl (private val apiService: ApiService): ListRepository {

    override suspend fun getList(): List<Item>? = apiService.getList()
}