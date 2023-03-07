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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.ecom.R
import com.example.ecom.adapters.ProductAdapter
import com.example.ecom.adapters.Specialproductsadapter
import com.example.ecom.data.Sp
import com.example.ecom.databinding.FragmentChairCategeryBinding
import com.example.ecom.databinding.FragmentTableCategeryBinding
import com.example.ecom.util.Resource
import com.example.ecom.util.show
import com.example.ecom.viewmodel.ChairViewModel
import com.example.ecom.viewmodel.HomeCategeryViewModel
import kotlinx.coroutines.flow.collectLatest

class chair_categery:Fragment() {
    private lateinit var spad: ProductAdapter
    private lateinit var binding: FragmentChairCategeryBinding

    private val viewmodel by viewModels<ChairViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentChairCategeryBinding.inflate(inflater)
        return (binding.root)
    }
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        SetupBestProductRV()

        spad.onItemClick={
            val b=Bundle().apply { putParcelable("product",it) }
            findNavController().navigate(R.id.action_homeFragment_to_detailedFragment,b)
        }

        lifecycleScope.launchWhenStarted {
            viewmodel.chair.collectLatest {
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

    private fun hideLoading() {
        binding.pb2.visibility=View.INVISIBLE
    }

    private fun showLoading() {
        binding.pb2.visibility=View.VISIBLE
    }

    private fun SetupBestProductRV() {

        spad=ProductAdapter()
        val snapHelper:SnapHelper=LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.chairrv)

        binding.chairrv.apply {
            layoutManager=GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)
            val  adapter=spad
            binding.chairrv.adapter=adapter

        }


    }
    override fun onResume() {
        super.onResume()
        show()
    }
}