package com.example.ecom.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.ecom.databinding.SpecialProductRvBinding
import com.example.ecom.databinding.ViewpagerImageItemBinding

class viewpagerimages : RecyclerView.Adapter<viewpagerimages.ViewHolder>() {

    inner class ViewHolder( val bindind: ViewpagerImageItemBinding):RecyclerView.ViewHolder(bindind.root){
                fun bind(imagepath:String){
                    Glide.with(itemView).load(imagepath).into(bindind.imageProductDetails)
                }
    }


    private val diffCallback =object :DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem==newItem
        }

    }

    val differ=AsyncListDiffer(this,diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ViewpagerImageItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )



    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bind(product)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size

    }

}