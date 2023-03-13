package com.example.ecom.viewmodel

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.ecom.Cart.adapter.CartAdapter
import com.example.ecom.R
import com.example.ecom.adapters.OrdersAdapter
import com.example.ecom.databinding.FragmentCartBinding
import com.example.ecom.databinding.FragmentOrdersBinding
import com.example.ecom.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.collectLatest

class OrdersFragment : Fragment() {

    private lateinit var spad: OrdersAdapter
    private val viewmodel by viewModels<OrdersViewmodel>()
    private lateinit var db: FirebaseFirestore
    private lateinit var auth:FirebaseAuth

    private lateinit var binding: FragmentOrdersBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.detach(this)
        fragmentTransaction.attach(this)
        fragmentTransaction.commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentOrdersBinding.inflate(layoutInflater, container, false)

        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.detach(this)
        fragmentTransaction.attach(this)
        fragmentTransaction.commit()

        var pr=0
        auth=FirebaseAuth.getInstance()
        db= FirebaseFirestore.getInstance()

        val coll =
            db.collection("users").document(auth.uid!!)
                .collection("Orders")
        coll.whereEqualTo("ref","9014").get().addOnSuccessListener { task ->
            for (document in task) {
                // Access the data of the matching document
                val name = document.getString("price")
                try{
                    pr+=name.toString().toDouble().toInt()
                }
                catch (e: NumberFormatException) {
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                }
            }
            binding.total.text= "Total Amount has to pay : ₹ "+pr.toString()

        }
            .addOnFailureListener {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }




        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        SetupSpecialProductRv()

        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.detach(this)
        fragmentTransaction.attach(this)
        fragmentTransaction.commit()



        spad.onItemClick = {

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

    }

    private fun showLoading() {
        binding.pb1.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.pb1.visibility = View.INVISIBLE
    }

    private fun SetupSpecialProductRv() {

        spad = OrdersAdapter()
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.item)
        binding.item.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            val adapter = spad
            binding.item.adapter = adapter
        }

    }

    override fun onResume() {
        super.onResume()
        spad.notifyDataSetChanged()
        binding.item.scheduleLayoutAnimation()

    }


}

