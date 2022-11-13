package com.example.fora_neo

import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.fora_neo.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseUser



class LoginFragment : Fragment() {
    private  lateinit var binding: FragmentLoginBinding


    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater, container, false)

        auth = Firebase.auth

        binding.button.setOnClickListener (View.OnClickListener {
            val email: String = binding.etUser.text.toString().trim() { it <= ' ' }
            val pw: String = binding.etPw.text.toString().trim() { it <= ' ' }

            if (validate (email, pw)){
                auth.signInWithEmailAndPassword(email, pw).addOnCompleteListener(requireActivity()){ task ->
                    if (task.isSuccessful) {
                        startapp()
                    } else {
                        Log.d("Tag", "Error", task.exception)
                    }
                }
            }
            else{
                Toast.makeText(activity, "Algunos campos pueden estar erroneos. Vuelva a intentarlo.", Toast.LENGTH_LONG).show()
            }
        })

        binding.tvSignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signup)
        }

        return binding.root
    }

    fun validate(email: String?, pw: String?): Boolean {
        return ((email != null) && (pw != null)) && (pw.length >= 5) && (email.contains("@"))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    fun startapp(){
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }
}
