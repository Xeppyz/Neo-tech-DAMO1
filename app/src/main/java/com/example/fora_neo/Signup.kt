package com.example.fora_neo

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.wifi.hotspot2.pps.Credential
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.fora_neo.databinding.FragmentSignupBinding
import com.firebase.ui.auth.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import org.json.JSONObject


class Signup : Fragment() {

    private lateinit var binding: FragmentSignupBinding
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignupBinding.inflate(inflater, container, false)



binding.btnRegresar.setOnClickListener {
    findNavController().navigate(R.id.action_signup_to_loginFragment)
}

        binding.btnSignup.setOnClickListener (View.OnClickListener {
            val email: String = binding.etCorreo.text.toString().trim() { it <= ' ' }
            val pw: String = binding.etPw.text.toString().trim() { it <= ' ' }
            val name:String = binding.etNombre.text.toString().trim() {it <= ' '}
            val secondName:String = binding.etApellido.text.toString().trim() {it <= ' '}
            val username:String = binding.etUsuario.text.toString().trim() {it <= ' '}


            //val confirm_pw: String

           signIn(email, pw, name, secondName, username)


        })

        return binding.root
    }
    fun signIn(email:String, pw:String, name:String, scndName: String, username: String){
        if (validate(email, pw)){
            auth.createUserWithEmailAndPassword(email, pw)
                .addOnSuccessListener {
                    val profile = UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build()
                    it.user!!.updateProfile(profile)
                        .addOnSuccessListener {
                            AlertDialog.Builder(activity).apply {
                                setTitle("Cuenta creada")
                                setMessage("Hola $name $scndName")
                                setPositiveButton("Aceptar"){ _: DialogInterface, _: Int ->
                                    findNavController().navigate(R.id.action_signup_to_loginFragment)
                                }
                            }.show()
                        }
                        .addOnFailureListener{
                            AlertDialog.Builder(activity).apply {
                                setTitle("Error")
                                setMessage(it.message)
                            }.show()
                        }

                   uploadUserInfo(name, scndName, username, email)
                }
                .addOnFailureListener{
                    AlertDialog.Builder(activity).apply {
                        setTitle("Error")
                        setMessage(it.message)
                    }.show()

                }





        }
        else{
            Toast.makeText(activity, "Algunos campos pueden estar erroneos. Vuelva a intentarlo.", Toast.LENGTH_LONG).show()
        }
    }

    fun validate(email: String?, pw: String?): Boolean {
        return ((email != null) && (pw != null)) && (pw.length >= 5) && (email.contains("@"))
    }
    fun uploadUserInfo(name:String, scndName:String, username:String, email:String){
        val currentUserUID = auth.currentUser!!.uid


        val userMap = HashMap<String, Any>()
        userMap["uid"] = currentUserUID
        userMap["name"] = name
        userMap["secondName"] = scndName
        userMap["email"] = email
        userMap["username"] = username

        firestore.collection("users").document(currentUserUID).set(userMap).addOnSuccessListener {
            Toast.makeText(activity, "Yes", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(activity, "no", Toast.LENGTH_SHORT).show()
        }



    }

}