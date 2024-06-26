package com.example.ecom.data

data class CartProduct(
    val Product: products,
    val quantity: Int,
    val selectedSizes: String? = null
) {
    constructor() : this(products(), 1, null)
}
