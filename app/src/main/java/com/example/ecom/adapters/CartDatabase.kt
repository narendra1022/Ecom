package com.example.ecom.adapters
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ecom.R
import com.example.ecom.data.CartDao
import com.example.ecom.data.CartItem

@Database(entities = [CartItem::class], version = 1)
abstract class CartDatabase: RoomDatabase() {

    abstract fun cartDao(): CartDao

    companion object {

        @Volatile
        private var INSTANCE: CartDatabase? = null

        fun getDatabase(context: Context): CartDatabase {

            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CartDatabase::class.java,
                    context.getString(R.string.cart_database)
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }

        }

    }
}