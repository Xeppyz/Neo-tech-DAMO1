package com.example.fora_neo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.fora_neo.databinding.FragmentUserBinding

class UserFragment : Fragment() {

    private lateinit var userBinding:FragmentUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userBinding = FragmentUserBinding.inflate(layoutInflater)
        return userBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userBinding.btnBack.setOnClickListener{
            it.findNavController().navigate(R.id.action_userFragment_to_homeFragment)
        }
    }

}