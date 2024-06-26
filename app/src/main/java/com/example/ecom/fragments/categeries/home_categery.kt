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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.ecom.R
import com.example.ecom.adapters.BestdealAdapter
import com.example.ecom.adapters.ProductAdapter
import com.example.ecom.adapters.Specialproductsadapter
import com.example.ecom.databinding.FragmentHomeCategeryBinding
import com.example.ecom.util.Resource
import com.example.ecom.util.show
import com.example.ecom.viewmodel.HomeCategeryViewModel

import kotlinx.coroutines.flow.collectLatest


class home_categery : Fragment(R.layout.fragment_home_categery) {

    private lateinit var spad: Specialproductsadapter
    private lateinit var bdpa: BestdealAdapter
    private lateinit var pa: ProductAdapter
    private lateinit var binding: FragmentHomeCategeryBinding

    private val viewmodel by viewModels<HomeCategeryViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeCategeryBinding.inflate(inflater)
        return (binding.root)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        SetupSpecialProductRv()
        SetupBestDealRV()
        SetupBestProductRV()

        spad.onItemClick = {
            val b = Bundle().apply { putParcelable("product", it) }
            findNavController().navigate(R.id.action_homeFragment_to_detailedFragment, b)
        }
        bdpa.onItemClick = {
            val b = Bundle().apply { putParcelable("product", it) }
            findNavController().navigate(R.id.action_homeFragment_to_detailedFragment, b)
        }
        pa.onItemClick = {
            val b = Bundle().apply { putParcelable("product", it) }
            findNavController().navigate(R.id.action_homeFragment_to_detailedFragment, b)
        }

        lifecycleScope.launchWhenStarted {
            viewmodel.specialproduct.collectLatest {
                when (it) {
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

                    else -> Unit

                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewmodel.bestdealproducts.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        showLoading()
                    }

                    is Resource.Success -> {
                        bdpa.differ.submitList(it.data)
                        hideLoading()
                    }

                    is Resource.Error -> {
                        hideLoading()

                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }

                    else -> Unit

                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewmodel.bestproducts.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        showLoading()
                    }

                    is Resource.Success -> {
                        pa.differ.submitList(it.data)
                        hideLoading()
                    }

                    is Resource.Error -> {
                        hideLoading()

                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }

                    else -> Unit

                }
            }
        }

    }

    private fun SetupBestDealRV() {

        bdpa = BestdealAdapter()
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.bestdealsrv)
        binding.bestdealsrv.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            val adapter = bdpa
            binding.bestdealsrv.adapter = adapter
        }

    }

    private fun SetupBestProductRV() {

        pa = ProductAdapter()
//        val snapHelper: SnapHelper = LinearSnapHelper()
//        snapHelper.attachToRecyclerView(binding.bestproductsrv)
        binding.bestproductsrv.apply {
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            val adapter = pa
            binding.bestproductsrv.adapter = adapter
        }

    }

    private fun showLoading() {
        binding.pb1.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.pb1.visibility = View.INVISIBLE
    }

    private fun SetupSpecialProductRv() {

        spad = Specialproductsadapter()
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.specialrv)
        binding.specialrv.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            val adapter = spad
            binding.specialrv.adapter = adapter
        }


    }

    override fun onResume() {
        super.onResume()
        show()
    }
}








