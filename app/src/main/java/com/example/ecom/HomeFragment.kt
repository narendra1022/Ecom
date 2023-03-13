package com.example.ecom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.ecom.adapters.basecategeryAdapter
import com.example.ecom.databinding.FragmentHomeBinding
import com.example.ecom.fragments.categeries.*
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(inflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categories = arrayListOf<Fragment>(
            home_categery(),
            table_categery(),
            furniture_categery(),
            cupboard_categery(),
            chair_categery()
        )

        binding.viewpager.isUserInputEnabled=false

        val v=basecategeryAdapter(categories,childFragmentManager,lifecycle)
        binding.viewpager.adapter=v
        val tabLayout=view.findViewById<TabLayout>(R.id.tablayout)
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL
        TabLayoutMediator(binding.tablayout,binding.viewpager){tab,position ->
            when(position){
                0->tab.text="Home"
                1->tab.text="Nike shoe"
                2->tab.text="Adidas shoe"
                3->tab.text="Puma shoe"
                4->tab.text="Reebok shoe"
            }

        }.attach()


    }


}