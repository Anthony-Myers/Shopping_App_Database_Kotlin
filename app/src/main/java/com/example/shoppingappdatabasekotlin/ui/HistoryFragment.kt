package com.example.shoppingappdatabasekotlin.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingappdatabasekotlin.databinding.HistoryFragmentBinding
import com.example.shoppingappdatabasekotlin.ui.adapters.OrderAdapter
import com.example.shoppingappdatabasekotlin.ui.data.entities.Order
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HistoryFragment : Fragment() {
    private lateinit var mViewModel: StoreViewModel
    private lateinit var mBinding: HistoryFragmentBinding
    private val mDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = HistoryFragmentBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(StoreViewModel::class.java)

        mDisposable.add(mViewModel.getListOfOrders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(this::onFailure)
                .doOnSuccess(this::onSuccess)
                .subscribe())

        mBinding.backToOrderButton.setOnClickListener {
            requireFragmentManager().popBackStack()
        }

        mViewModel.getOrderLiveData().observe(viewLifecycleOwner, Observer{
            if(mViewModel.getOrderList().isNotEmpty()){
                mBinding.itemRv.apply {
                    mBinding.itemRv.adapter = OrderAdapter(mViewModel.getOrderList())
                    mBinding.itemRv.layoutManager = LinearLayoutManager(context)
                    addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
                }
            }
        } )
    }

    private fun onSuccess(orders: List<Order>) {
        mBinding.backToOrderButton.isEnabled = true
        mBinding.loader.visibility = ProgressBar.INVISIBLE
        mViewModel.createOrderList(orders)
    }

    private fun onFailure(error: Throwable) {
        Log.d(TAG, error.toString())
    }

    companion object {
        private val TAG = HistoryFragment::class.simpleName
    }
}