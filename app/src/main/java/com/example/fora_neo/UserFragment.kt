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
import com.google.firebase.auth.FirebaseAuth

class UserFragment : Fragment() {

    private lateinit var userBinding: FragmentUserBinding
    private lateinit var toggle: ActionBarDrawerToggle
    var auth = FirebaseAuth.getInstance()


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

        with(userBinding){
            tvNombreUsuario.text = auth.currentUser!!.displayName
            btnBack.setOnClickListener {
                it.findNavController().navigate(R.id.action_userFragment_to_homeFragment)
            }

            btnSettings.setOnClickListener{
                if (layoutSetting.visibility == View.VISIBLE){
                    layoutSetting.visibility = View.GONE
                }
                else{
                    layoutSetting.visibility = View.VISIBLE
                }
            }

            btnGuardar.setOnClickListener {
                validar()
            }
        }

    }

    private fun validar(){
        with(userBinding){
            if (etCorreo.text.isBlank() && etPw.text.isBlank() && etPwConfirm.text.isBlank()){
                Toast.makeText(activity, "No hay ningun dato ingresado", Toast.LENGTH_SHORT).show()
            }
            else if (etCorreo.text.isBlank() && etPw.text.isNotBlank() && etPwConfirm.text.isNotBlank()){
                Toast.makeText(activity, "Contrasena guardada", Toast.LENGTH_SHORT).show()
            }
            else if (etCorreo.text.isNotBlank() && etPw.text.isBlank() && etPwConfirm.text.isBlank()){
                Toast.makeText(activity, "Correo guardado", Toast.LENGTH_SHORT).show()
            }
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