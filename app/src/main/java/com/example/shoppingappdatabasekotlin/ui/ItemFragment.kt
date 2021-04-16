package com.example.shoppingappdatabasekotlin.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingappdatabasekotlin.databinding.ItemFragmentBinding
import com.example.shoppingappdatabasekotlin.ui.adapters.ItemAdapter
import com.example.shoppingappdatabasekotlin.ui.data.entities.Item
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ItemFragment : Fragment() {
    private lateinit var mViewModel: StoreViewModel
    private lateinit var mBinding: ItemFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = ItemFragmentBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(StoreViewModel::class.java)


        var itemAdapter: ItemAdapter = ItemAdapter(mViewModel.getListOfPersistedItems(), this@ItemFragment::addItemToOrder)
        requireActivity().runOnUiThread{
            mBinding.itemRv.apply {
                layoutManager = LinearLayoutManager(requireContext())
                val dividerItemDecoration: DividerItemDecoration = DividerItemDecoration(mBinding.itemRv.context, DividerItemDecoration.VERTICAL)
                addItemDecoration(dividerItemDecoration)
                mBinding.itemRv.adapter = itemAdapter
            }
        }
    }

    private fun addItemToOrder(item: Item) {
        mViewModel.addItemToOrder(item)
    }
}