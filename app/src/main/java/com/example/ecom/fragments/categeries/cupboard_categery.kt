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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecom.R
import com.example.ecom.adapters.BestdealAdapter
import com.example.ecom.databinding.FragmentCupboardCategeryBinding
import com.example.ecom.util.Resource
import com.example.ecom.util.show
import com.example.ecom.viewmodel.CupboardViewModal
import kotlinx.coroutines.flow.collectLatest

class cupboard_categery : Fragment() {

    private lateinit var bdap: BestdealAdapter
    private lateinit var binding: FragmentCupboardCategeryBinding

    private val viewmodel by viewModels<CupboardViewModal>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCupboardCategeryBinding.inflate(inflater)
        return (binding.root)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)


        setupBestProductRV()

        bdap.onItemClick = {
            val b = Bundle().apply { putParcelable("product", it) }
            findNavController().navigate(R.id.action_homeFragment_to_detailedFragment, b)
        }

        lifecycleScope.launchWhenStarted {
            viewmodel.cup.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        showLoading()
                    }

                    is Resource.Success -> {
                        bdap.differ.submitList(it.data)
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

    private fun hideLoading() {
        binding.pb4.visibility = View.INVISIBLE
    }

    private fun showLoading() {
        binding.pb4.visibility = View.VISIBLE
    }

    private fun setupBestProductRV() {

        bdap = BestdealAdapter()


        binding.cupboardrv.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            val adapter = bdap
            binding.cupboardrv.adapter = adapter

        }


    }

    override fun onResume() {
        super.onResume()
        show()
    }


}


