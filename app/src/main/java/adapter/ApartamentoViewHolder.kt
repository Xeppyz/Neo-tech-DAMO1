package adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.fora_neo.databinding.ApartamentoLayoutBinding
import datos.Apartamento
import datos.ListaApartamentos

class ApartamentoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val binding = ApartamentoLayoutBinding.bind(view)
    fun renderizar (apartamento: Apartamento) {
        binding.tvNombreApartamento.text = apartamento.nombreApartamento
        binding.tvDireccionApartamento.text = apartamento.direccionApartamento
        binding.tvPrecioApartamento.text = apartamento.precioApartamento
    }
}