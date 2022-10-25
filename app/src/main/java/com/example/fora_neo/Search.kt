package com.example.fora_neo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SearchView
import com.example.fora_neo.databinding.ActivitySearchBinding

class Search : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val apartamentos = arrayOf("Casa", "Universidad", "Metro")
        val apartamentosAdapter : ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, apartamentos)

        binding.listView.adapter = apartamentosAdapter

        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()

                if (apartamentos.contains(query)){
                    apartamentosAdapter.filter.filter(query)
                }
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                apartamentosAdapter.filter.filter(query)
                return false
            }
        })
    }
}