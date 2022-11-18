package com.example.fora_neo

import adapter.ApartamentoAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fora_neo.databinding.FragmentBaratosBinding
import com.example.fora_neo.databinding.FragmentRecomendadosBinding
import com.google.firebase.firestore.FirebaseFirestore
import datos.Apartamento


class BaratosFragment : Fragment() {
    private lateinit var binding: FragmentBaratosBinding
    private lateinit var lista:MutableList<Apartamento>
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBaratosBinding.inflate(layoutInflater)

        db.collection("apartamentos").addSnapshotListener { value, error ->
            val apartamentos = value!!.toObjects(Apartamento::class.java)
            apartamentos.forEachIndexed { index, apartamento ->
                apartamento.uid = value.documents[index].id


            }
            lista = apartamentos
            prueba(lista)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnback.setOnClickListener {
            findNavController().navigate(R.id.action_baratosFragment_to_homeFragment)
        }
    }

    fun inicializarRecyclerView(listaApart: List<Apartamento>) {
        binding.rvApartamento.setHasFixedSize(true)
        binding.rvApartamento.layoutManager = LinearLayoutManager(activity)
        binding.rvApartamento.adapter = ApartamentoAdapter(listaApart, activity)

    }
    fun prueba(lista : List<Apartamento>){
        var aux = ArrayList<Apartamento>()
        lista.forEachIndexed { index, apartamento ->
            if (apartamento.precioApartamento!!.toDouble() <= 5000.00){
                aux.add(apartamento)
                inicializarRecyclerView(aux)
            }
        }

    }
}