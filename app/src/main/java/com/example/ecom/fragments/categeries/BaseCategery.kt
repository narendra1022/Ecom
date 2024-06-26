package com.example.ecom.fragments.categeries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecom.R
import com.example.ecom.adapters.ProductAdapter
import com.example.ecom.databinding.FragmentBaseCategeryBinding

open class BaseCategery : Fragment(R.layout.fragment_base_categery) {

    private lateinit var binding: FragmentBaseCategeryBinding
    private lateinit var offerAdapter: ProductAdapter
    private lateinit var bestProductAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBaseCategeryBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupOfferrv()
        setupBestProductsRv()

    }

    private fun setupBestProductsRv() {

        bestProductAdapter = ProductAdapter()

        binding.bestproductsrv.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            adapter = bestProductAdapter
            binding.bestproductsrv.adapter = adapter
        }


    }

    private fun setupOfferrv() {


        offerAdapter = ProductAdapter()

        binding.offerrv.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            adapter = offerAdapter
            binding.bestproductsrv.adapter = adapter
        }


    }


}