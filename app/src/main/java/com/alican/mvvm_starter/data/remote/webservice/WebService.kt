package com.alican.mvvm_starter.data.remote.webservice

import com.alican.mvvm_starter.data.remote.dto.GetDataDto
import com.alican.mvvm_starter.base.BaseResponse
import retrofit2.Response
import retrofit2.http.GET

interface WebService {

    @GET("endpoint")
    suspend fun getData(): BaseResponse<Any>

    @GET("endpoint")
    suspend fun getDataRepo(): Response<GetDataDto>
}
