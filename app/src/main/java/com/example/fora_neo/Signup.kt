package com.example.fora_neo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.fora_neo.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class Signup : Fragment() {

    private lateinit var binding: FragmentSignupBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignupBinding.inflate(inflater, container, false)

        auth = Firebase.auth

        binding.btnSignup.setOnClickListener (View.OnClickListener {
            val email: String = binding.etUser.text.toString().trim() { it <= ' ' }
            val pw: String = binding.etPw.text.toString().trim() { it <= ' ' }
            //val confirm_pw: String

            if (validate(email, pw)){
                auth.createUserWithEmailAndPassword(email, pw).addOnCompleteListener(requireActivity()){ task ->
                    if (task.isSuccessful) {
                        findNavController().navigate(R.id.action_signup_to_loginFragment)

                    } else {
                        Log.d("Tag", "Error", task.exception)
                    }
                }
            }
            else{
                Toast.makeText(activity, "Algunos campos pueden estar erroneos. Vuelva a intentarlo.", Toast.LENGTH_LONG).show()
            }
        })

        return binding.root
    }

    fun validate(email: String?, pw: String?): Boolean {
        return ((email != null) && (pw != null)) && (pw.length >= 5) && (email.contains("@"))
    }
}