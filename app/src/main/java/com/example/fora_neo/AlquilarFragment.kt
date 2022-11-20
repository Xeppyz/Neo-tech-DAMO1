package com.example.fora_neo

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.fora_neo.databinding.FragmentAlquilarBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import datos.Apartamento


class AlquilarFragment : Fragment() {
    private lateinit var binding: FragmentAlquilarBinding
    lateinit var toggle: ActionBarDrawerToggle
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    lateinit var userFragment: UserFragment
    private var storageApartPicRef: StorageReference? = null
    private lateinit var imageUri: Uri
    private var apid: String = " "


    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            binding.imgCasa.setImageURI(uri)

            imageUri = uri
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentAlquilarBinding.inflate(layoutInflater)
        storageApartPicRef = FirebaseStorage.getInstance().reference.child("pics")
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

        binding.imgVolver.setOnClickListener {
            it.findNavController().navigate(R.id.action_alquilarFragment_to_homeFragment)
        }

        binding.imgCasa.setOnClickListener {
            iniciarFoto()
        }
        binding.btnConfirmarDatos.setOnClickListener {
            val nomApartamento = binding.etNombreApartamento.text.toString()
            val direccion = binding.etDireccionApartamento.text.toString()
            val precio = binding.etPrecioApartamento.text.toString().trim() { it <= ' ' }
            val nombreUs = auth.currentUser!!.displayName

            val apartamento = Apartamento(
                nomApartamento,
                direccion,
                precio,
                nombreUs,
                "template",
                auth.currentUser!!.uid
            )

            db.collection("apartamentos").add(apartamento)
                .addOnSuccessListener {
                    val idap = it.id
                    apid = idap
                    Log.i("idap", idap)
                }
                .addOnFailureListener {
                    android.app.AlertDialog.Builder(activity).apply {
                        setTitle("Error")
                        setMessage(it.message)
                    }.show()
                }
                .addOnCompleteListener {
                    uploadPic()
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
                else -> requestPermissionLaucher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        } else {
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
            imgCasa.setImageResource(R.drawable.casa)
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

    fun uploadPic() {
        var downloadUrl =
            "https://images.all-free-download.com/images/graphiclarge/graphics_template_211519.jpg"
        val fileRef = storageApartPicRef!!.child("$apid.jpg")
        fileRef.putFile(imageUri).addOnSuccessListener {
            val uriTask = it.storage.downloadUrl

            while (!uriTask.isSuccessful);


            if (uriTask.isSuccessful) {
                uriTask.addOnSuccessListener { uri ->
                    downloadUrl = uri.toString()
                    Log.i("download", downloadUrl)
                    val doc = db.collection("apartamentos").document(apid)
                    db.runTransaction {
                        it.update(doc, "imageUrl", downloadUrl)
                        null
                    }
                }
                    .addOnFailureListener {
                        Log.i("download", downloadUrl)
                    }
            }
        }
    }

}