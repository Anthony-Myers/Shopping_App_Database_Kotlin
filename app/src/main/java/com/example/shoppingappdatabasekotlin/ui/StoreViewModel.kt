package com.example.shoppingappdatabasekotlin.ui

import android.app.Application
import android.graphics.BitmapFactory
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppingappdatabasekotlin.ui.data.entities.Item
import com.example.shoppingappdatabasekotlin.ui.data.entities.Order
import com.example.shoppingappdatabasekotlin.ui.data.repos.StoreRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class StoreViewModel(application: Application) : AndroidViewModel(application){
    private val mStoreRepository : StoreRepository = StoreRepository(application)
    private var mOrderItemsLiveData: MutableLiveData<ArrayList<Item>> = MutableLiveData()
    private var mOrderItemsList: ArrayList<Item> = ArrayList()
    private lateinit var mItemsList: List<Item>
    private lateinit var mOrdersList: List<Order>
    private var mOrdersLiveData: MutableLiveData<List<Order>> = MutableLiveData()

    fun createOrderList(orderList: List<Order>){
        mOrdersList = orderList
        mOrdersLiveData.postValue(mOrdersList)
    }

    fun getOrderList(): List<Order>{return mOrdersList}

    fun getOrderLiveData(): LiveData<List<Order>>{return mOrdersLiveData}

    fun addItemToOrder(item: Item){
        mOrderItemsList.add(item)
        mOrderItemsLiveData.postValue(mOrderItemsList)
    }

    fun removeItemFromOrder(item: Item){
        mOrderItemsList.remove(item)
        mOrderItemsLiveData.postValue(mOrderItemsList)
    }

    fun clearItemsInOrder(){
        mOrderItemsList.clear()
        mOrderItemsLiveData.postValue(mOrderItemsList)
    }

    fun getItemsInOrderLiveData():LiveData<ArrayList<Item>>{return mOrderItemsLiveData}

    fun getItemsInOrder():ArrayList<Item>{return mOrderItemsList}

    fun getListOfItems() : Single<List<Item>> { return mStoreRepository.getAllItems() }

    fun populateItemDb() : Completable{return mStoreRepository.populateItems(getApplication())}

    fun clearItemDb() : Completable{return mStoreRepository.deleteItems()}

    fun addOrderToHistory(order: Order): Completable{return mStoreRepository.addOrder(order)}

    fun getListOfOrders() : Single<List<Order>> {return mStoreRepository.getAllOrders()}

    fun addListOfItems(items: List<Item>){mItemsList = items}

    fun getListOfPersistedItems(): List<Item>{return mItemsList}

    fun getOrderTotal(): Double{
        var total: Double = 0.0
        for(item in mOrderItemsList){
            total += item.getPrice()
        }
        return total
    }
}