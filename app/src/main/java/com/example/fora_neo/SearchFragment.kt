package com.example.fora_neo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.navigation.findNavController
import com.example.fora_neo.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {

private lateinit var binding: FragmentSearchBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {

        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchView.setOnClickListener {

            it.findNavController().navigate(R.id.action_homeFragment_to_searchFragment)

        }

        binding.imageView2.setOnClickListener {

            it.findNavController().navigate(R.id.action_searchFragment_to_homeFragment)
        }
    }




}