package com.example.fora_neo

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil.setContentView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.fora_neo.databinding.ActivityMainBinding
import com.example.fora_neo.databinding.FragmentHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import datos.Usuario


@Suppress("UNREACHABLE_CODE")
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    lateinit var toggle: ActionBarDrawerToggle
    private val db = FirebaseFirestore.getInstance()
    lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var usuario:Usuario

    var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHomeBinding.inflate(layoutInflater)

        /*val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        toggle = ActionBarDrawerToggle(activity, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()*/
        db.collection("users").document(auth.currentUser!!.uid).addSnapshotListener { value, error ->
            usuario = value!!.toObject(Usuario::class.java)!!
            binding.tvDisplayName.text = usuario.username!!.toString()
            Log.i("username", usuario.username!!.toString())
        }



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root



    }

    @Suppress("DEPRECATION")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val navView: BottomNavigationView = binding.navView


        navView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.user -> replaceFragment()
                R.id.house -> Toast.makeText(context, "Ya estás en Home", Toast.LENGTH_SHORT).show()

            }
            true
        }

        binding.alquilarDep.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_alquilarFragment)
        }
        binding.buscarDep.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
        binding.tvLogOut.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
            auth.signOut()
        }
        binding.recomendados.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_recomendadosFragment)
        }
        binding.baratos.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_baratosFragment)
        }




        /*Toast.makeText(activity, usuario.username, Toast.LENGTH_SHORT).show()*/



    }

    private fun replaceFragment() {
        findNavController().navigate(R.id.action_homeFragment_to_userFragment)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return true
    }


}