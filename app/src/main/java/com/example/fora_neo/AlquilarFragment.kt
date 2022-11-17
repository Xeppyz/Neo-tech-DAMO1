package com.example.fora_neo

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
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

    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null)
        {
            binding.imgCasa.setImageURI(uri)
        }

    }

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

        binding.imgCasa.setOnClickListener {
            iniciarFoto()
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

    private fun iniciarFoto() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(
                    binding.imgCasa.context, Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    lanzarFoto()
                }
                else-> requestPermissionLaucher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }else{
            lanzarFoto()
        }
    }

    private val requestPermissionLaucher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            lanzarFoto()
        } else {
            Toast.makeText(context, "Habilitar los permisos", Toast.LENGTH_SHORT).show()
        }

    }


    private fun lanzarFoto() {
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun limpiar() {
        with(binding) {
            etPrecioApartamento.setText(" ")
            etNombreApartamento.setText(" ")
            etDireccionApartamento.setText(" ")
            etNombreApartamento.requestFocus()
        }
    }

    private fun fragmentBuscar() {
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