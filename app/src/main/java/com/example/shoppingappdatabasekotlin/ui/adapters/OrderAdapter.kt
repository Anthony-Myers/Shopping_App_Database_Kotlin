package com.example.shoppingappdatabasekotlin.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingappdatabasekotlin.MainActivity
import com.example.shoppingappdatabasekotlin.R
import com.example.shoppingappdatabasekotlin.databinding.HistoryItemBinding
import com.example.shoppingappdatabasekotlin.ui.data.entities.Order

class OrderAdapter (private var mOrderList: List<Order>
    ) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    fun addOrders(orders : List<Order>) {
        mOrderList = orders
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderAdapter.OrderViewHolder =
            OrderViewHolder(
                    HistoryItemBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                    )
            )

    override fun getItemCount(): Int = mOrderList.size

    override fun onBindViewHolder(holder: OrderAdapter.OrderViewHolder, position: Int) = with(holder) {
        val order = mOrderList[position]
        loadData(order)

        holder.getRecyclerView().layoutManager = LinearLayoutManager(holder.getRecyclerView().context, LinearLayoutManager.HORIZONTAL, false)
        holder.getRecyclerView().adapter = ItemAdapter(order.getItemList()){}
        val dividerItemDecoration: DividerItemDecoration = DividerItemDecoration(holder.getRecyclerView().context, DividerItemDecoration.VERTICAL)
        holder.getRecyclerView().addItemDecoration(dividerItemDecoration)
    }

    class OrderViewHolder(private val binding: HistoryItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun loadData(order: Order) = with(binding) {
            orderNumberTv.text = binding.root.context.resources.getString(R.string.order_number, order.getOrderNumber().toString())
            numberOfItemsTv.text = binding.root.context.resources.getString(R.string.number_of_items, order.getItemList().size.toString())
            totalTv.text = binding.root.context.resources.getString(R.string.total, order.getTotalCost().format(2))
            itemRv.layoutManager = LinearLayoutManager(binding.root.context)
            itemRv.adapter = ItemAdapter(order.getItemList()) {}
        }

        fun getRecyclerView()=  binding.itemRv

        private fun Double.format(digits: Int) = "%.${digits}f".format(this)

    }
}