package com.example.fora_neo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.fora_neo.databinding.FragmentUserBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class UserFragment : Fragment() {

    private lateinit var userBinding: FragmentUserBinding
    lateinit var toggle: ActionBarDrawerToggle


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userBinding = FragmentUserBinding.inflate(layoutInflater)
        return userBinding.root

    }

    @Suppress("DEPRECATION")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navView: BottomNavigationView = userBinding.navViewUser


        navView.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.house -> replaceFragment()
                R.id.user -> Toast.makeText(context, "Ya estás en usuario", Toast.LENGTH_SHORT).show()
            }
            true
        }




        userBinding.btnBack.setOnClickListener {
            it.findNavController().navigate(R.id.action_userFragment_to_homeFragment)
        }


    }

    private fun replaceFragment() {
        findNavController().navigate(R.id.action_userFragment_to_homeFragment)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {

            return true
        }
        return true
    }


}