package com.example.ecom.data

import androidx.room.*

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_table")
    fun getAllCartItems(): MutableList<CartItem>

    @Query("SELECT count(*) from cart_table")
    fun getNumber():Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(cartItem: CartItem)
    @Query("Delete from cart_table where cart_item_id=:name")
    fun delCart(name: String)

    @Query("Update cart_table set quantity=:qty where cart_item_id=:name")
    fun updateQty(qty:Int,name:String)

    @Query("select count(*) from cart_table where cart_item_id=:name")
    fun findIt(name:String):Int

    @Query("select (quantity) from cart_table where cart_item_id=:name")
    fun getQty(name:String):Int

    @Query("select count(*) from cart_table")
    fun getQty2():Int

//    @Query("UPDATE notification_table SET notification_status=:notificationStatus WHERE notification_id LIKE :notificationId ")
//    fun update(notificationStatus: String?, notificationId: String?)

//    @Query("DELETE FROM notification_table WHERE Id IN (SELECT Id FROM notification_table ORDER BY Id ASC LIMIT 1)")
//    fun delete()

//    @Query("SELECT COUNT(*) FROM notification_table ")
//    fun count(): Int
}