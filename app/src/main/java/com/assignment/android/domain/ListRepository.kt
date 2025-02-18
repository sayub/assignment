package com.assignment.android.domain

import com.assignment.android.model.Item

interface ListRepository {

    suspend fun getList() : List<Item>?
}