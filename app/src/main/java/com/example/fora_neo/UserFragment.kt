package com.example.fora_neo

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
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

            btnCorreo.setOnClickListener{
                val correo = etCorreo.text.toString()
                validarCorreo(correo)
            }
            btnGuardarClave.setOnClickListener {
                val clave = etPw.text.toString().trim() {it <= ' '}
                val clave2 = etPwConfirm.text.toString().trim() {it <= ' '}

                validarClave(clave, clave2)
            }
        }

    }

    private fun validarCorreo(correo:String){
        if (correo.isBlank()){
           Toast.makeText(context, "Debes de ingresar un correo",Toast.LENGTH_SHORT).show()
        }
        else if(correo.equals(auth.currentUser!!.email)){
            Toast.makeText(context, "El correo debe ser diferente al actual", Toast.LENGTH_SHORT).show()
        }
        else{
            android.app.AlertDialog.Builder(activity).apply {
                setTitle("Cambio de correo")
                setMessage("¿Estas seguro que quieres cambiar el correo?")
                setPositiveButton("Aceptar"){ _: DialogInterface, _: Int ->
                    auth.currentUser!!.updateEmail(correo)
                }
                setNegativeButton("Cancelar"){ _: DialogInterface, _: Int ->

                }
            }.show()


        }

    }

    private fun validarClave(clave:String, clave2:String){
        if (clave.equals(clave2)){
            android.app.AlertDialog.Builder(activity).apply {
                setTitle("Cambio de correo")
                setMessage("¿Estas seguro que quieres cambiar el correo?")
                setPositiveButton("Aceptar"){ _: DialogInterface, _: Int ->
                    auth.currentUser!!.updatePassword(clave)
                }
                setNegativeButton("Cancelar"){ _: DialogInterface, _: Int ->

                }
            }.show()

        }
        else{
            Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
        }
        Toast.makeText(context, "Contraseña actualizada", Toast.LENGTH_SHORT).show()
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