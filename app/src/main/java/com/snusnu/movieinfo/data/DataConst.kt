package com.snusnu.movieinfo.data

import com.snusnu.movieinfo.BuildConfig

object DataConst {

    const val API_GET_MOVIES_BY_NAME = "${BuildConfig.API_URL}search-by-keyword"
    const val PARAM_PAGE = "page"
    const val PARAM_KEYWORD = "keyword"
    const val HEADERS_X_API_KEY = "X-API-KEY"
    const val X_API_KEY = "cdf92501-86e2-4b65-a3ef-08b9b82375a8"
    const val HEADERS_CONTENT_TYPE = "Content-Type"
    const val CONTENT_TYPE = "application/json"
    const val COUNT_OF_PAGE = "1"
}