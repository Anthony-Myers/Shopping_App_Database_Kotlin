package com.example.shoppingappdatabasekotlin.ui.data.dao

import androidx.room.*
import com.example.shoppingappdatabasekotlin.ui.data.entities.Item
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ItemDao {
    @Transaction
    @Query("SELECT * FROM items")
    fun getAllItems() : Single<List<Item>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(items : List<Item>) : Completable

    @Query("DELETE FROM items")
    fun deleteItems() : Completable
}