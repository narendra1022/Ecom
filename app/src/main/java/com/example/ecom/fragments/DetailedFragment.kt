package com.example.ecom.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecom.R
import com.example.ecom.activities.shopping
import com.example.ecom.adapters.CartDatabase
import com.example.ecom.adapters.colorAdapter
import com.example.ecom.adapters.viewpagerimages
import com.example.ecom.data.CartItem
import com.example.ecom.data.CartProduct
import com.example.ecom.data.product
import com.example.ecom.databinding.FragmentDetailedBinding
import com.example.ecom.util.Resource
import com.example.ecom.util.hide
import com.example.ecom.viewmodel.DetailsViewModal
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import dagger.hilt.android.AndroidEntryPoint
import io.grpc.NameResolver.Args
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint

class DetailedFragment : Fragment() {

    private val pro by navArgs<DetailedFragmentArgs>()
    private lateinit var db: FirebaseFirestore
    private lateinit var binding:FragmentDetailedBinding
    private val viewPagerAdapter by lazy { viewpagerimages() }
    private val colorAdapter by lazy { colorAdapter() }
    private lateinit var cartDb: CartDatabase
    private lateinit var auth: FirebaseAuth

    private var slectedSizes:String?=null
    private val viewmodel by viewModels<DetailsViewModal>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        hide()
        binding=FragmentDetailedBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
        setupColor()
        db=FirebaseFirestore.getInstance()
        auth=FirebaseAuth.getInstance()

        val produc=pro.product

        colorAdapter.onItemClick={
            slectedSizes=it
        }

        binding.buttonAddToCart.setOnClickListener {



            val li= hashMapOf("name" to binding.Name.text.toString() ,"price" to binding.price.text.toString() ,"dezcription" to binding.Descriptiion.text.toString() ,"images" to produc.images)


            db.collection("users")
                .document(auth.uid!!).collection("cart").add(
                    li
                    ).addOnSuccessListener {
                    Toast.makeText(requireContext(), "Added to cart", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }


        }

        binding.close.setOnClickListener{
            findNavController().navigateUp()
        }

        binding.apply {
            Name.text=produc.name
            price.text= produc.price.toString()
            Descriptiion.text=produc.description
        }

        viewPagerAdapter.differ.submitList(produc.images)
        produc.sizes?.let { colorAdapter.differ.submitList(it) }

    }

    private fun setupColor() {

        binding.colorRv.apply {
            adapter=colorAdapter
            layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        }

    }
    private fun setupViewPager() {

        binding.apply {
            viewpager.adapter=viewPagerAdapter
        }

    }
}