package com.example.ecom.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecom.adapters.colorAdapter
import com.example.ecom.adapters.viewpagerimages
import com.example.ecom.databinding.FragmentDetailedBinding
import com.example.ecom.util.hide
import com.example.ecom.viewmodel.DetailsViewModal
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class DetailedFragment : Fragment() {

    private val pro by navArgs<DetailedFragmentArgs>()

    private lateinit var db: FirebaseFirestore
    private lateinit var binding: FragmentDetailedBinding
    private val viewPagerAdapter by lazy { viewpagerimages() }
    private val colorAdapter by lazy { colorAdapter() }
    private lateinit var auth: FirebaseAuth

    private var slectedSizes: String? = null
    private val viewmodel by viewModels<DetailsViewModal>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        hide()
        binding = FragmentDetailedBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
        setupColor()
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        val produc = pro.product

        colorAdapter.onItemClick = {
            slectedSizes = it
        }

        binding.buttonAddToCart.setOnClickListener {

            val li = hashMapOf(
                "name" to binding.Name.text.toString(),
                "price" to binding.price.text,
                "dezcription" to binding.description.text.toString(),
                "images" to produc.images,
                "ref" to "9014"
            )

            binding.buttonAddToCart.startAnimation()

            db.collection("users")
                .document(auth.uid!!).collection("cart").add(
                    li
                ).addOnSuccessListener {
                    Toast.makeText(requireContext(), "Added to cart", Toast.LENGTH_SHORT).show()
                    binding.buttonAddToCart.revertAnimation()
                    binding.buttonAddToCart.setBackgroundColor(Color.DKGRAY)
                }.addOnFailureListener {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }


        }

        binding.close.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.apply {
            Name.text = produc.name
            price.text = produc.price.toString()
            description.text = produc.description
        }

        viewPagerAdapter.differ.submitList(produc.images)
        produc.sizes?.let { colorAdapter.differ.submitList(it) }

    }

    private fun setupColor() {

        binding.colorRv.apply {
            adapter = colorAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

    }

    private fun setupViewPager() {

        binding.apply {
            viewpager.adapter = viewPagerAdapter
        }

    }
}