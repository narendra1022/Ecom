package com.example.ecom.adapters

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecom.R
import com.example.ecom.data.Sp
import com.example.ecom.data.product
import com.example.ecom.databinding.BestDealRvBinding
import com.example.ecom.databinding.SpecialProductRvBinding

class BestdealAdapter: RecyclerView.Adapter<BestdealAdapter.viewHolder>() {

    var onItemClick: ((product) -> Unit)? = null

    inner class viewHolder(val binding: BestDealRvBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(product: product){

            binding.apply {
                Glide.with(itemView).load(product.images?.get(0)).into(ProductImg)
                name.text=product.name
                nprice.text="₹"+product.price.toString()
                oprice.text="₹"+product.offerPercentage.toString()
                oprice.paintFlags= Paint.STRIKE_THRU_TEXT_FLAG
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
    ):viewHolder {
        return viewHolder(
            BestDealRvBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: viewHolder, position: Int) {

        val product = differ.currentList[position]
        holder.bind(product)


        holder.itemView.setOnClickListener {
            onItemClick?.invoke(differ.currentList[position])
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}