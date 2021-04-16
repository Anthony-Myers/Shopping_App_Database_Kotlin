package com.example.shoppingappdatabasekotlin.ui.adapters

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingappdatabasekotlin.MainActivity
import com.example.shoppingappdatabasekotlin.R
import com.example.shoppingappdatabasekotlin.databinding.StoreItemBinding
import com.example.shoppingappdatabasekotlin.ui.data.entities.Item

class ItemAdapter(
        private val mItemList: List<Item>,
        private val listener: ((item: Item) -> Unit)
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ItemViewHolder =
            ItemViewHolder(
                    StoreItemBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                    )
            )

    override fun getItemCount(): Int = mItemList.count()

    override fun onBindViewHolder(holder: ItemAdapter.ItemViewHolder, position: Int) = with(holder) {
        val item = mItemList[position]
        loadData(item)
        holder.itemView.setOnClickListener { listener(item) }
    }

    class ItemViewHolder(private val binding: StoreItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
                fun loadData(item: Item) = with(binding) {
                    nameTv.text = binding.root.context.resources.getString(R.string.name, item.getName())
                    skuTv.text = binding.root.context.resources.getString(R.string.sku, item.getSku())
                    priceTv.text = binding.root.context.resources.getString(R.string.price, item.getPrice().format(2))
                    productIv.setImageBitmap(byteArrayToBitmap(item.getImage()))
                }

                private fun Double.format(digits: Int) = "%.${digits}f".format(this)

                private fun byteArrayToBitmap(image: ByteArray): Bitmap{
                        return BitmapFactory.decodeByteArray(image , 0, image.size)
                }
            }
    }