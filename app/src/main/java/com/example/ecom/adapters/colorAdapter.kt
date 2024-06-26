package com.example.ecom.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ecom.databinding.SizeRvItemBinding

class colorAdapter : RecyclerView.Adapter<colorAdapter.SizesAdapterViewHolder>() {


    var onItemClick: ((String) -> Unit)? = null


    inner class SizesAdapterViewHolder(val binding: SizeRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(size: String, position: Int) {
            binding.tvSize.text = size
            if (position == selectedPosition) { //Select case
                binding.apply {
                    imgShadow.visibility = View.VISIBLE
                }
            } else { // size is not selected
                binding.apply {
                    imgShadow.visibility = View.INVISIBLE
                }
            }

        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SizesAdapterViewHolder {
        return SizesAdapterViewHolder(
            SizeRvItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    private var selectedPosition = -1

    override fun onBindViewHolder(holder: SizesAdapterViewHolder, position: Int) {
        val size = differ.currentList[position]
        holder.bind(size, position)
        holder.itemView.setOnClickListener {

            if (selectedPosition >= 0) {
                notifyItemChanged(selectedPosition)
                selectedPosition = holder.adapterPosition
                notifyItemChanged(selectedPosition)
                onItemClick?.invoke(size)

            }
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}

