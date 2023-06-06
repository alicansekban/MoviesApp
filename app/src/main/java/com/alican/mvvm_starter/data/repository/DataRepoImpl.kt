package com.alican.mvvm_starter.data.repository

import com.alican.mvvm_starter.data.local.model.DataModel
import com.alican.mvvm_starter.data.remote.dto.toDataModel
import com.alican.mvvm_starter.data.remote.webservice.WebService
import com.alican.mvvm_starter.domain.repository.DataRepo
import com.alican.mvvm_starter.util.ErrorType
import com.alican.mvvm_starter.util.Resource
import java.net.SocketTimeoutException
import javax.inject.Inject

class DataRepoImpl @Inject constructor(
    private val webService: WebService
) : DataRepo {
    override suspend fun getData(): Resource<DataModel> {
        try {
            val task = webService.getDataRepo()
            if (task.isSuccessful) {
                task.body()?.let {
                    return Resource.Success(data = it.toDataModel())
                } ?: return Resource.Error(errorType = ErrorType.EMPTY_DATA)
            } else {
                return Resource.Error()
            }
        } catch (e: SocketTimeoutException) {
            return Resource.Error(errorType = ErrorType.TIME_OUT)
        } catch (e: Exception) {
            return Resource.Error(message = e.localizedMessage)
        }
    }
}
