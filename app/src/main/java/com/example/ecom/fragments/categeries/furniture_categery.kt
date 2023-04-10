package com.example.ecom.fragments.categeries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.*
import com.example.ecom.R
import com.example.ecom.adapters.ProductAdapter
import com.example.ecom.adapters.Specialproductsadapter
import com.example.ecom.data.SpecialProductDataClass
import com.example.ecom.databinding.FragmentFurnitureCategeryBinding
import com.example.ecom.util.Resource
import com.example.ecom.util.show
import com.example.ecom.viewmodel.FurnitureViewModal
import kotlinx.coroutines.flow.collectLatest

class furniture_categery:Fragment() {

    private lateinit var spa:Specialproductsadapter
    private lateinit var binding:FragmentFurnitureCategeryBinding

    private val viewmodel by viewModels<FurnitureViewModal>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFurnitureCategeryBinding.inflate(inflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SetupBestProductRV()

        spa.onItemClick={
            val b=Bundle().apply { putParcelable("product",it) }
            findNavController().navigate(R.id.action_homeFragment_to_detailedFragment,b)
        }

        lifecycleScope.launchWhenStarted {
            viewmodel.c.collectLatest {
                when(it) {
                    is Resource.Loading -> {
                        showLoading()
                    }
                    is Resource.Success -> {
                        spa.differ.submitList(it.data)
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

    private fun hideLoading() {
        binding.pb5.visibility=View.INVISIBLE
    }

    private fun showLoading() {
        binding.pb5.visibility=View.VISIBLE
    }

    private fun SetupBestProductRV() {

        spa=Specialproductsadapter()


        binding.furniturerv.apply {
            layoutManager= LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            val  adapter=spa
            binding.furniturerv.adapter=adapter

        }


    }
    override fun onResume() {
        super.onResume()
        show()
    }

    }

