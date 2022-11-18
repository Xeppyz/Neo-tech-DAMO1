package com.example.fora_neo

import adapter.ApartamentoAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fora_neo.databinding.FragmentSearchBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import datos.Apartamento
import datos.ListaApartamentos


class SearchFragment : Fragment() {

private lateinit var binding: FragmentSearchBinding
private val db = FirebaseFirestore.getInstance()
   private var busq = ""

private lateinit var lista:MutableList<Apartamento>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {

        binding = FragmentSearchBinding.inflate(layoutInflater)

        db.collection("apartamentos").addSnapshotListener { value, error ->
            val apartamentos = value!!.toObjects(Apartamento::class.java)
            apartamentos.forEachIndexed { index, apartamento ->
                apartamento.uid = value.documents[index].id


            }
            lista = apartamentos
            inicializarRecyclerView(lista)
        }




        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var aux = ""

        binding.btnSearch.setOnClickListener {
            aux = binding.etBuscar.text.toString().uppercase()
            prueba(aux)
        }
        binding.imageView2.setOnClickListener {

            it.findNavController().navigate(R.id.action_searchFragment_to_homeFragment)
        }




    }

    fun inicializarRecyclerView(listaApart:List<Apartamento>){
        binding.rvApartamento.setHasFixedSize(true)
        binding.rvApartamento.layoutManager = LinearLayoutManager(activity)
        binding.rvApartamento.adapter = ApartamentoAdapter(listaApart, activity)
    }

    fun prueba(busqueda:String){
        var aux = ArrayList<Apartamento>()
        lista.forEachIndexed { index, apartamento ->
            if (busqueda.isNotBlank()){
                if (apartamento.direccionApartamento!!.uppercase().equals(busqueda)){
                   aux.add(apartamento)
                }
                inicializarRecyclerView(aux)
            }else{
                inicializarRecyclerView(lista)
            }
        }

    }



}