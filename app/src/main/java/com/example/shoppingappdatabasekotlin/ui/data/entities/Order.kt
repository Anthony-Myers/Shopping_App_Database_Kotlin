package com.example.shoppingappdatabasekotlin.ui.data.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
        @NonNull
        @ColumnInfo(typeAffinity = ColumnInfo.INTEGER)
        @PrimaryKey(autoGenerate = true)
        private var orderNumber: Int,

        @ColumnInfo
        private var itemList: List<Item>
) {

    fun getOrderNumber(): Int {
        return orderNumber;
    }

    fun getItemList(): List<Item> {
        return itemList;
    }

    fun getTotalCost(): Double {
        var total: Double = 0.0
        for (x in itemList) {
            total += x.getPrice()
        }
        return total
    }
}