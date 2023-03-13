package com.example.ecom.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.ecom.R
import com.example.ecom.data.addressData
import com.example.ecom.fragments.PlaceOrderFragment


class addressDataAdapter(
    private val context: Context,
    private val detailsList: ArrayList<addressData>
) :
    RecyclerView.Adapter<addressDataAdapter.DetailsViewHolder>() {

    class DetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.text_full_name)
        val line1: TextView = itemView.findViewById(R.id.text_address_line_1)
        val village: TextView = itemView.findViewById(R.id.text_address_line_2)
        val city: TextView = itemView.findViewById(R.id.text_city)
        val district: TextView = itemView.findViewById(R.id.text_district)
        val state: TextView = itemView.findViewById(R.id.text_state)
        val pincode: TextView = itemView.findViewById(R.id.text_zip_code)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.address_layout, parent, false)
        return DetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        val currentProduct = detailsList[position]

        holder.name.text = currentProduct.name
        holder.name.text = currentProduct.name
        holder.line1.text = currentProduct.line1
        holder.village.text = currentProduct.line2
        holder.district.text = currentProduct.district
        holder.city.text = currentProduct.city
        holder.state.text = currentProduct.state
        holder.pincode.text = currentProduct.pincode

        holder.itemView.setOnClickListener {

            val fragment = PlaceOrderFragment()
            val bundle = Bundle()
            bundle.putString("name", currentProduct.name)
            bundle.putString("l1", currentProduct.line1)
            bundle.putString("l2", currentProduct.line2)
            bundle.putString("dist", currentProduct.district)
            bundle.putString("city", currentProduct.city)
            bundle.putString("stat", currentProduct.state)
            bundle.putString("pin", currentProduct.pincode)
            fragment.arguments = bundle

            val activity = it.context as AppCompatActivity
            activity.supportFragmentManager.beginTransaction().addToBackStack(null)
                .replace(R.id.fram, fragment).commit()



        }

    }

    override fun getItemCount(): Int {
        return detailsList.size
    }


}
