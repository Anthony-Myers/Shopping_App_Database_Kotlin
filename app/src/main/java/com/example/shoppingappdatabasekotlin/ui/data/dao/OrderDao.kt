package com.example.shoppingappdatabasekotlin.ui.data.dao

import androidx.room.*
import com.example.shoppingappdatabasekotlin.ui.data.entities.Order
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface OrderDao {

    @Transaction
    @Query("SELECT * FROM orders")
    fun getOrders() : Single<List<Order>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrder(order : Order) : Completable

    @Query("DELETE FROM orders")
    fun deleteOrders() : Completable
}