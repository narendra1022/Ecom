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
import com.example.ecom.data.Sp
import com.example.ecom.data.SpecialProductDataClass
import com.example.ecom.databinding.FragmentHomeCategeryBinding
import com.example.ecom.databinding.FragmentTableCategeryBinding
import com.example.ecom.util.Resource
import com.example.ecom.util.show
import com.example.ecom.viewmodel.TableViewModal
import kotlinx.coroutines.flow.collectLatest

class table_categery:Fragment(){
    private lateinit var pa:ProductAdapter
    private lateinit var binding: FragmentTableCategeryBinding
    private val viewmodel by viewModels<TableViewModal>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTableCategeryBinding.inflate(inflater)
        return (binding.root)
    }
    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        SetupBestProductR()


        pa.onItemClick={
            val b=Bundle().apply { putParcelable("product",it) }
            findNavController().navigate(R.id.action_homeFragment_to_detailedFragment,b)
        }

        lifecycleScope.launchWhenStarted {
            viewmodel.table.collectLatest {
                when(it) {
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
                    else ->Unit

                }
            }
        }

    }

    private fun hideLoading() {
        binding.pb3.visibility=View.INVISIBLE
    }

    private fun showLoading() {
        binding.pb3.visibility=View.VISIBLE
    }

    private fun SetupBestProductR() {

        pa= ProductAdapter()
        binding.tablerv.apply {
            layoutManager=GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)
            val  adapter=pa
            binding.tablerv.adapter=adapter

        }


    }
    override fun onResume() {
        super.onResume()
        show()
    }


}