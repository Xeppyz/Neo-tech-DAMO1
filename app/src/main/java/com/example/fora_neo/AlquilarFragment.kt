package com.example.fora_neo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.fora_neo.databinding.FragmentAlquilarBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import datos.Apartamento


class AlquilarFragment : Fragment() {
    private lateinit var binding: FragmentAlquilarBinding
    lateinit var toggle: ActionBarDrawerToggle
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
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
        val navView: BottomNavigationView = binding.navVivienda


        navView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.house -> fragmentHome()
                R.id.user -> fragmentUsuario()
                R.id.vivienda -> fragmentBuscar()

            }
            true
        }
        binding.btnConfirmarDatos.setOnClickListener {
            val nomApartamento = binding.etNombreApartamento.text.toString()
            val direccion = binding.etDireccionApartamento.text.toString()
            val precio = binding.etPrecioApartamento.text.toString().trim() { it <= ' ' }
            val nombreUs = auth.currentUser!!.displayName

            val apartamento = Apartamento(nomApartamento, direccion, precio, nombreUs)

            db.collection("apartamentos").add(apartamento)
                .addOnSuccessListener {

                }
                .addOnFailureListener {
                    android.app.AlertDialog.Builder(activity).apply {
                        setTitle("Error")
                        setMessage(it.message)
                    }.show()
                }


            limpiar()

        }

    }

    private fun limpiar() {
        with(binding) {
            etPrecioApartamento.setText(" ")
            etNombreApartamento.setText(" ")
            etDireccionApartamento.setText(" ")
            etNombreApartamento.requestFocus()
        }
    }

    private fun fragmentBuscar(){
        findNavController().navigate(R.id.action_alquilarFragment_to_searchFragment)
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