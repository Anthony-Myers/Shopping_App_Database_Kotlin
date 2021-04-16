package com.example.shoppingappdatabasekotlin.ui.data.repos

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.Room
import com.example.shoppingappdatabasekotlin.R
import com.example.shoppingappdatabasekotlin.ui.data.dao.ItemDao
import com.example.shoppingappdatabasekotlin.ui.data.dao.OrderDao
import com.example.shoppingappdatabasekotlin.ui.data.database.Database
import com.example.shoppingappdatabasekotlin.ui.data.entities.Item
import com.example.shoppingappdatabasekotlin.ui.data.entities.Order
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.io.ByteArrayOutputStream

class StoreRepository(application: Application) {

    private val db = Room.databaseBuilder(
            application,
            Database::class.java, Database.NAME
    ).build()

    private val mOrderDao: OrderDao = db.orderDao()
    private val mItemDao: ItemDao = db.itemDao()


    fun getAllOrders(): Single<List<Order>> {
        return mOrderDao.getOrders()
    }

    fun getAllItems(): Single<List<Item>> {
        return mItemDao.getAllItems()
    }

    fun addOrder(order: Order): Completable {
        return mOrderDao.insertOrder(order)
    }

    fun addItems(itemList: List<Item>) {
        mItemDao.insertItems(itemList)
    }

    fun deleteItems(): Completable {
        return mItemDao.deleteItems()
    }

    fun populateItems(application: Application): Completable {
        val item1: Item = Item(1, "Doge", 15.22, "M3M3_JK101H3H3_69", bitmapToByteArray(BitmapFactory.decodeResource(application.resources, R.drawable.doge_product)))
        val item2: Item = Item(2, "The Ming", 232.21, "SDSDEWEW_420_JJL", bitmapToByteArray(BitmapFactory.decodeResource(application.resources, R.drawable.the_ming_product)))
        val item3: Item = Item(3, "Crychael Jordan", 25.62, "JEILJSD_34_JAKDF", bitmapToByteArray(BitmapFactory.decodeResource(application.resources, R.drawable.crychael_jordan)))
        val item4: Item = Item(4, "E", 53.19, "JKSLDF_908_JSKDLFJ", bitmapToByteArray(BitmapFactory.decodeResource(application.resources, R.drawable.e_product)))
        val item5: Item = Item(5, "Stonks", 23.28, "JLJLJL3_JSDLK_342J", bitmapToByteArray(BitmapFactory.decodeResource(application.resources, R.drawable.stonks_product)))
        val item6: Item = Item(6, "Cat Fight", 77.91, "ASD_3849S_JDKF9", bitmapToByteArray(BitmapFactory.decodeResource(application.resources, R.drawable.cat_fight_product)))

        return mItemDao.insertItems(listOf(item1, item2, item3, item4, item5, item6))
    }

    private fun bitmapToByteArray(image: Bitmap): ByteArray{
        val stream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 1, stream)
        return stream.toByteArray()
    }
}