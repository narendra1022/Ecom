package com.example.ecom

import android.content.Context
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
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.ecom.Cart.adapter.CartAdapter
import com.example.ecom.databinding.FragmentCartBinding
import com.example.ecom.util.Resource
import com.example.ecom.viewmodel.CartViewmodel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.collectLatest

class cartFragment : Fragment() {

    private lateinit var spad: CartAdapter
    private val viewmodel by viewModels<CartViewmodel>()
    private lateinit var binding: FragmentCartBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(layoutInflater, container, false)

        setupSpecialProductRv()

        binding.tvProceed.setOnClickListener {
            FirebaseFirestore.getInstance().collection("users")
                .document(FirebaseAuth.getInstance().uid!!).collection("Addresses")
                .get().addOnSuccessListener { docs ->
                    if (docs.isEmpty) {
                        findNavController().navigate(R.id.action_cartFragment_to_addressFragment)
                    } else {
                        findNavController().navigate(R.id.action_cartFragment_to_addressesListFragment)
                    }
                }
        }

        lifecycleScope.launchWhenStarted {
            viewmodel.specialproduct.collectLatest {
                when (it) {
                    is Resource.Loading -> showLoading()
                    is Resource.Success -> {
                        spad.differ.submitList(it.data)
                        hideLoading()
                    }

                    is Resource.Error -> {
                        hideLoading()
                        Toast.makeText(requireContext(), "Error: ${it.message}", Toast.LENGTH_SHORT)
                            .show()
                    }

                    else -> Unit
                }
            }
        }

        return binding.root
    }

    private fun showLoading() {
        binding.pb1.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.pb1.visibility = View.INVISIBLE
    }

    private fun setupSpecialProductRv() {
        spad = CartAdapter()
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.item)
        binding.item.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = spad
        }
    }
}
