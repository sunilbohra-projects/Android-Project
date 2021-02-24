package com.target.targetcasestudy.model

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET

interface ProductResponseApi {

    @SerializedName("products")
    @GET("mobile_case_study_deals/v1/deals")
    fun getProducts() : Call<ProductList>
}

