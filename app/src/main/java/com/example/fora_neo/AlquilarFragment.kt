package com.example.fora_neo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.fragment.findNavController
import com.example.fora_neo.databinding.FragmentAlquilarBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class AlquilarFragment : Fragment() {
    private lateinit var binding: FragmentAlquilarBinding
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var userFragment: UserFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentAlquilarBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlquilarBinding.inflate(layoutInflater)
        return binding.root


    }
    @Suppress("DEPRECATION")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navView: BottomNavigationView = binding.navView


        navView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.house -> fragmentHome()
                R.id.user ->fragmentUsuario()


            }
            true
        }

    }

    private fun fragmentHome() {
        findNavController().navigate(R.id.action_alquilarFragment_to_homeFragment)
    }
    private fun fragmentUsuario() {
        findNavController().navigate(R.id.action_alquilarFragment_to_userFragment)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {


            return true

        }
        return true
    }

}