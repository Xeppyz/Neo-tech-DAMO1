package com.example.fora_neo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.fora_neo.databinding.FragmentHomeBinding
import com.example.fora_neo.databinding.FragmentInfoBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import datos.Apartamento

class InfoFragment : Fragment() {
    private lateinit var binding:FragmentInfoBinding
    private val db = FirebaseFirestore.getInstance()
    private lateinit var apartamento: Apartamento

        var id = " "
    var contexto = " "
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         id = requireArguments().getString("pid").toString()

         contexto = requireArguments().getString("context").toString()


         db.collection("apartamentos").document(id).addSnapshotListener { value, error ->
             apartamento = value!!.toObject(Apartamento::class.java)!!
             binding.tvNombreApartamento.text = apartamento.nombreApartamento
             binding.tvDireccionApartamento.text = apartamento.direccionApartamento
             binding.tvPrecioApartamento.text = apartamento.precioApartamento.toString()
             binding.tvPropietario.text = apartamento.nombreUsuario
             Picasso.get()
                 .load(apartamento.imageUrl)
                 .error(R.mipmap.ic_launcher_round)
                 .into(binding.ivApartamento)
         }




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoBinding.inflate(layoutInflater)




        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnBack.setOnClickListener {
            if (contexto.equals("baratos")){
                findNavController().navigate(R.id.action_infoFragment_to_baratosFragment)
            }
            else if(contexto.equals("recomendados")){
                findNavController().navigate(R.id.action_infoFragment_to_recomendadosFragment)
            }
            else{
                findNavController().navigate(R.id.action_infoFragment_to_searchFragment)
            }
        }

    }



}