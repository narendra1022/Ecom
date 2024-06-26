package com.example.ecom.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cart_table")
data class CartItem(
    @PrimaryKey(autoGenerate = true) val Id: Int?,
    @ColumnInfo(name = "cart_item_id") val cartItemId: String?,
    @ColumnInfo(name = "cart_item_name") val cartItemName: String?,
    @ColumnInfo(name = "price_per_unit") val pricePerUnit: String?,
    @ColumnInfo(name = "quantity") val quantity: Int?,
    @ColumnInfo(name = "subtotal") val subtotal: Int?
)