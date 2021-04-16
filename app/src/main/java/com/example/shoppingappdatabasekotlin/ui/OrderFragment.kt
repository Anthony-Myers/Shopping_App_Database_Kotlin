package com.example.shoppingappdatabasekotlin.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingappdatabasekotlin.R
import com.example.shoppingappdatabasekotlin.databinding.OrderFragmentBinding
import com.example.shoppingappdatabasekotlin.ui.adapters.ItemAdapter
import com.example.shoppingappdatabasekotlin.ui.adapters.ItemInOrderAdapter
import com.example.shoppingappdatabasekotlin.ui.data.entities.Item
import com.example.shoppingappdatabasekotlin.ui.data.entities.Order
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class OrderFragment : Fragment() {
    private lateinit var mBinding: OrderFragmentBinding
    private lateinit var mViewModel: StoreViewModel
    private val mDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = OrderFragmentBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = ViewModelProvider(requireActivity()).get(StoreViewModel::class.java)

        mViewModel.getItemsInOrderLiveData().observe(viewLifecycleOwner, Observer {
            mBinding.chargeButton.isEnabled = mViewModel.getItemsInOrder().size != 0

            mBinding.orderRv.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = ItemInOrderAdapter(mViewModel.getItemsInOrder(), this@OrderFragment::deleteItemFromOrder)
                val dividerItemDecoration: DividerItemDecoration = DividerItemDecoration(mBinding.orderRv.context, DividerItemDecoration.VERTICAL)
                addItemDecoration(dividerItemDecoration)
            }
            mBinding.chargeButton.text = getString(R.string.format_charge, mViewModel.getOrderTotal().format(2))
            mBinding.itemCount.text = resources.getQuantityString(R.plurals.item_count, mViewModel.getItemsInOrder().size, mViewModel.getItemsInOrder().size.toString())
            mBinding.chargeButton.setOnClickListener(View.OnClickListener {
                val newOrder: Order = Order(0, mViewModel.getItemsInOrder())
                mBinding.chargeButton.isEnabled = false
                mDisposable.add(mViewModel.addOrderToHistory(newOrder)
                        .subscribeOn(Schedulers.io())
                        .doOnError { Log.d(OrderFragment::class.simpleName.toString(), "Could not add Order to database") }
                        .doOnComplete {
                            mViewModel.clearItemsInOrder()
                            requireActivity().runOnUiThread{ mBinding.chargeButton.isEnabled = true }
                        }
                        .subscribe())
            })
        })
        mBinding.historyButton.setOnClickListener(View.OnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.history_fragment, HistoryFragment())
                    .addToBackStack(null).commit()
        })
    }

    private fun Double.format(digits: Int) = "%.${digits}f".format(this)

    private fun deleteItemFromOrder(item: Item) {
        mViewModel.removeItemFromOrder(item)
    }

}