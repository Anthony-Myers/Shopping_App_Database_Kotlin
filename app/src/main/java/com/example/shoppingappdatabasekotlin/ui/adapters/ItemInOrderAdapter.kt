package com.example.shoppingappdatabasekotlin.ui.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingappdatabasekotlin.MainActivity
import com.example.shoppingappdatabasekotlin.R
import com.example.shoppingappdatabasekotlin.databinding.InOrderItemBinding
import com.example.shoppingappdatabasekotlin.ui.data.entities.Item

class ItemInOrderAdapter (
        private val mItemList: ArrayList<Item>,
        private val mListener: ((item: Item) -> Unit)
) : RecyclerView.Adapter<ItemInOrderAdapter.ItemInOrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemInOrderAdapter.ItemInOrderViewHolder =
            ItemInOrderViewHolder(
                    InOrderItemBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                    )
            )

    override fun getItemCount(): Int = mItemList.size

    override fun onBindViewHolder(holder: ItemInOrderAdapter.ItemInOrderViewHolder, position: Int) = with(holder) {
        val item = mItemList[position]
        loadData(item)
        holder.getBinding().removeItemB.setOnClickListener{mListener(item)}
    }

    class ItemInOrderViewHolder(private val binding: InOrderItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun loadData(item: Item) = with(binding) {
            nameTv.text = binding.root.context.resources.getString(R.string.name, item.getName())
            skuTv.text = binding.root.context.resources.getString(R.string.sku, item.getSku())
            priceTv.text = binding.root.context.resources.getString(R.string.price, item.getPrice().format(2))
            productIv.setImageBitmap(byteArrayToBitmap(item.getImage()))
        }
        fun getBinding():InOrderItemBinding{return binding}

        private fun Double.format(digits: Int) = "%.${digits}f".format(this)

        private fun byteArrayToBitmap(image: ByteArray): Bitmap {
            return BitmapFactory.decodeByteArray(image , 0, image.size)
        }
    }
}
