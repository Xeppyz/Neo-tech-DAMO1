package adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fora_neo.R
import datos.Apartamento
import datos.ListaApartamentos
import java.lang.Appendable

class ApartamentoAdapter(private val listaApartamento:List<Apartamento>):RecyclerView.Adapter<ApartamentoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApartamentoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ApartamentoViewHolder(layoutInflater.inflate(R.layout.apartamento_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ApartamentoViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return listaApartamento.size
    }


}