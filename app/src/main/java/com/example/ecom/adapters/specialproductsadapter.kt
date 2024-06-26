package com.example.ecom.adapters

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecom.data.product
import com.example.ecom.databinding.SpecialProductRvBinding


class Specialproductsadapter : RecyclerView.Adapter<Specialproductsadapter.viewHolder>() {

    var onItemClick: ((product) -> Unit)? = null

    inner class viewHolder(val binding: SpecialProductRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: product) {

            binding.apply {
                Glide.with(itemView).load(product.images?.get(0)).into(ProductImg)
                ProductName.text = product.name
                ProductNewPrice.text = "₹" + product.price.toString()
                ProductOldPrice.text = "₹" + product.offerPercentage.toString()
                ProductOldPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<product>() {
        override fun areItemsTheSame(oldItem: product, newItem: product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: product, newItem: product): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): viewHolder {
        return viewHolder(
            SpecialProductRvBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: viewHolder, position: Int) {

        val product = differ.currentList[position]
        holder.bind(product)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(product)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}

