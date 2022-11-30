package com.fabien.klaxitwork.api

import com.fabien.klaxitwork.models.AdressModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

public interface SearchApi {
    @Headers(
        "Accept: application/json"
    )
    @GET("search")
    abstract fun getSearchResult(
        @Query("q") value: String,
        @Query("autocomplete") autoComplete: Int = 0,
        @Query("limit") limit: Int = 30
    ): Call<AdressModel>?
}