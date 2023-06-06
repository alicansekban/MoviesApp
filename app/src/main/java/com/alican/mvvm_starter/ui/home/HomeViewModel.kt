package com.alican.mvvm_starter.ui.home

import androidx.lifecycle.ViewModel
import com.murgupluoglu.request.RESPONSE
import com.murgupluoglu.request.request
import com.alican.mvvm_starter.data.remote.webservice.WebService
import com.alican.mvvm_starter.util.SingleLiveEvent
import com.alican.mvvm_starter.base.BaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
   private val webService: WebService

) : ViewModel() {
    val dataResponse: SingleLiveEvent<RESPONSE<BaseResponse<Any>>> =
        SingleLiveEvent()
    fun getData() {
        dataResponse.request({webService.getData()})
    }
}
