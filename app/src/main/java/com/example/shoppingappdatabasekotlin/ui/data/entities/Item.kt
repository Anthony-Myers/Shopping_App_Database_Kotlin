package com.example.shoppingappdatabasekotlin.ui.data.entities

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item (
        @PrimaryKey (autoGenerate = true)
        private var id : Int,

        @ColumnInfo
        private var name : String,

        @ColumnInfo
        private var price : Double,

        @ColumnInfo
        private var sku : String,

        @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
        private var image : ByteArray){

    fun getId() : Int {return id}

    fun getName() : String {return name;}

    fun getPrice() : Double {return price;}

    fun getSku() : String {return sku;}

    fun getImage() : ByteArray {return image;}

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Item

        if (id != other.id) return false
        if (name != other.name) return false
        if (price != other.price) return false
        if (sku != other.sku) return false
        if (!image.contentEquals(other.image)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + price.hashCode()
        result = 31 * result + sku.hashCode()
        result = 31 * result + image.contentHashCode()
        return result
    }
}