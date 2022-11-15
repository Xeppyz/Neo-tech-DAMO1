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

private lateinit var lista:List<Apartamento>
  private  var apartamento = Apartamento(
        "Marcelos house",
        "villa 9 de junio",
        "100.00",
        "Marcelo")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {

        binding = FragmentSearchBinding.inflate(layoutInflater)

        db.collection("apartamentos").addSnapshotListener { value, error ->
            val apartamentos = value!!.toObjects(Apartamento::class.java)
            apartamentos.forEachIndexed { index, apartamento ->
                apartamento.uid = value.documents[index].id

                inicializarRecyclerView(apartamentos)
            }
        }



        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchView.setOnClickListener {

            it.findNavController().navigate(R.id.action_homeFragment_to_searchFragment)

        }

        binding.imageView2.setOnClickListener {

            it.findNavController().navigate(R.id.action_searchFragment_to_homeFragment)
        }


    }

    fun inicializarRecyclerView(listaApart:List<Apartamento>){
        binding.rvApartamento.setHasFixedSize(true)
        binding.rvApartamento.layoutManager = LinearLayoutManager(activity)
        binding.rvApartamento.adapter = ApartamentoAdapter(listaApart)
    }




}