package com.example.ecom.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecom.R
import com.example.ecom.adapters.addressDataAdapter
import com.example.ecom.data.addressData
import com.example.ecom.databinding.FragmentAddressesListBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddressesListFragment : Fragment() {


    private lateinit var binding: FragmentAddressesListBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var currentSeller: ArrayList<addressData>
    private lateinit var auth: FirebaseAuth
    private lateinit var proceed: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAddressesListBinding.inflate(layoutInflater, container, false)


        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        currentSeller = arrayListOf()

//        binding.another.visibility = View.GONE

        binding.addressRv.layoutManager = LinearLayoutManager(requireContext())


        db.collection("users").document(auth.uid!!).collection("Addresses").get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    for (data in it.documents) {
                        val user: addressData? = data.toObject(addressData::class.java)
                        if (user != null) {
                            currentSeller.add(user)
                        }
                    }
                    binding.addressRv.adapter = addressDataAdapter(requireContext(), currentSeller)
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }

        val collectionReference =
            db.collection("users").document(auth.uid!!)
                .collection("Addresses")
        collectionReference.get().addOnSuccessListener { task ->
            if (task.size().toString() <= "2") {
                binding.another.visibility = View.VISIBLE
            }
        }

        binding.another.setOnClickListener {
            findNavController().navigate(R.id.action_addressesListFragment_to_addressFragment)
        }

//        binding.next.setOnClickListener {
//            findNavController().navigate(R.id.action_addressesListFragment_to_placeOrderFragment)
//        }

        return binding.root
    }
}