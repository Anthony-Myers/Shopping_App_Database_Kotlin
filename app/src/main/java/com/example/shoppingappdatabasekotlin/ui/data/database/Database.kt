package com.example.shoppingappdatabasekotlin.ui.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.shoppingappdatabasekotlin.ui.data.converter.Converter
import com.example.shoppingappdatabasekotlin.ui.data.dao.ItemDao
import com.example.shoppingappdatabasekotlin.ui.data.dao.OrderDao
import com.example.shoppingappdatabasekotlin.ui.data.entities.Item
import com.example.shoppingappdatabasekotlin.ui.data.entities.Order
import kotlin.reflect.KClass

@androidx.room.Database(entities = [Item::class, Order::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class Database : RoomDatabase() {
    companion object {
        @JvmStatic
        val NAME : String = "StoreDatabase"

        @Volatile
        private var INSTANCE : Database? = null

        fun getDatabase(context : Context) : Database{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        Database::class.java,
                        NAME).build()
                INSTANCE = instance
                instance
            }
        }
    }

    abstract fun orderDao() : OrderDao
    abstract fun itemDao() : ItemDao
}