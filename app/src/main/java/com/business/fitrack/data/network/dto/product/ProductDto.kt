package com.business.fitrack.data.network.dto.product

import com.business.fitrack.data.network.dto.common.ProductData
import com.google.gson.annotations.SerializedName

data class ProductDto(

    @SerializedName("code")
    val code: String,

    @SerializedName("status")
    val status: Int,

    @SerializedName("product")
    val data: ProductData,
)

