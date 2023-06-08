package com.alican.mvvm_starter.data.model

import com.alican.mvvm_starter.data.remote.response.Dates
import com.alican.mvvm_starter.data.remote.response.ResultsItem
import com.squareup.moshi.Json

data class NowPlayingMoviesModel(
    var id : Int,
    var dates: Dates,
    var page: Int? = null,
    var totalPages: Int? = null,
    var results: List<ResultsItem?>? = null,
    var totalResults: Int? = null,
    var isSelected : Boolean = false

)