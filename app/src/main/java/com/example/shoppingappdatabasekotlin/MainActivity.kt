package com.example.shoppingappdatabasekotlin

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shoppingappdatabasekotlin.databinding.MainActivityBinding
import com.example.shoppingappdatabasekotlin.ui.ItemFragment
import com.example.shoppingappdatabasekotlin.ui.OrderFragment
import com.example.shoppingappdatabasekotlin.ui.StoreViewModel
import com.example.shoppingappdatabasekotlin.ui.adapters.ItemAdapter
import com.example.shoppingappdatabasekotlin.ui.data.entities.Item
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private lateinit var mViewModel: StoreViewModel
    private var mDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var mBinding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = MainActivityBinding.inflate(layoutInflater)

        mViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))
                .get(StoreViewModel::class.java)

        setContentView(R.layout.main_activity)

        supportFragmentManager.beginTransaction()
                .replace(R.id.order_fragment, OrderFragment())
                .addToBackStack(null).commit()



        val sharedPreferences : SharedPreferences = getPreferences(Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences.edit()

        if(!sharedPreferences.getBoolean("isPopulated", false)){
            editor.putBoolean("isPopulated", true).apply()
            mDisposable.add(mViewModel.populateItemDb().subscribeOn(Schedulers.io())
                    .doOnComplete { mDisposable.add(mViewModel.getListOfItems()
                            .subscribeOn(Schedulers.io())
                            .doOnError(this::onFailure)
                            .doOnSuccess(this::onSuccess)
                            .subscribe()) }
                    .doOnError{this::onFailure}
                    .subscribe())
        }
        else{
            mDisposable.add(mViewModel.getListOfItems()
                    .subscribeOn(Schedulers.io())
                    .doOnError(this::onFailure)
                    .doOnSuccess(this::onSuccess)
                    .subscribe())
        }
    }

    override fun onDestroy() {
        mDisposable.dispose()
        super.onDestroy()
    }

    private fun onSuccess(items: List<Item>) {
        mBinding.loader.visibility = ProgressBar.INVISIBLE
        mViewModel.addListOfItems(items)
        supportFragmentManager.beginTransaction()
                .replace(R.id.item_fragment, ItemFragment())
                .addToBackStack(null).commit()
    }

    private fun onFailure(error: Throwable) {
        Log.d(MainActivity::class.simpleName, error.toString())
    }

}