package com.example.shoppingappdatabasekotlin.ui.data.converter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import com.example.shoppingappdatabasekotlin.R
import com.example.shoppingappdatabasekotlin.ui.data.entities.Item
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class Converter {
    companion object{
        @JvmStatic
        @TypeConverter
        fun fromItemList(item: List<Item?>?): String? {
            val type = object : TypeToken<List<Item>>() {}.type
            return Gson().toJson(item, type)
        }
        @JvmStatic
        @TypeConverter
        fun toItemList(itemString: String?): List<Item>? {
            val type = object : TypeToken<List<Item>>() {}.type
            return Gson().fromJson<List<Item>>(itemString, type)
        }
    }
}