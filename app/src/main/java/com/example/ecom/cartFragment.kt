package com.example.ecom

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.ecom.Cart.adapter.CartAdapter
import com.example.ecom.adapters.CartDatabase
import com.example.ecom.adapters.Specialproductsadapter
import com.example.ecom.data.CartItem
import com.example.ecom.data.orderData
import com.example.ecom.databinding.FragmentCartBinding
import com.example.ecom.util.Resource
import com.example.ecom.viewmodel.CartViewmodel
import com.example.ecom.viewmodel.HomeCategeryViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest

class cartFragment : Fragment() {

    private lateinit var spad: CartAdapter
    private val viewmodel by viewModels<CartViewmodel>()
    private lateinit var db: FirebaseFirestore

    private lateinit var binding: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SetupSpecialProductRv()

        spad.onItemClick={
//            val b=Bundle().apply { putParcelable("product",it) }
//            findNavController().navigate(R.id.action_cartFragment_to_detailedFragment)
        }

        lifecycleScope.launchWhenStarted {
            viewmodel.specialproduct.collectLatest {
                when(it) {
                    is Resource.Loading -> {
                        showLoading()
                    }
                    is Resource.Success -> {
                        spad.differ.submitList(it.data)
                        hideLoading()
                    }
                    is Resource.Error -> {
                        hideLoading()

                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }
                    else ->Unit

                }
            }
        }

    }

    private fun showLoading() {
        binding.pb1.visibility=View.VISIBLE
    }

    private fun hideLoading() {
        binding.pb1.visibility=View.INVISIBLE
    }

    private fun SetupSpecialProductRv() {

        spad= CartAdapter()
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.item)
        binding.item.apply {
            layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            val adapter=spad
            binding.item.adapter=adapter
        }


    }


}

