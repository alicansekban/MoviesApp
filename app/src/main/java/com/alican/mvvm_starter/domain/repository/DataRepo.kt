package com.alican.mvvm_starter.domain.repository

import com.alican.mvvm_starter.data.local.model.DataModel
import com.alican.mvvm_starter.util.Resource

interface DataRepo {
    suspend fun getData(): Resource<DataModel>
}
