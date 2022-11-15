package adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.fora_neo.R
import com.example.fora_neo.databinding.ApartamentoLayoutBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import datos.Apartamento
import datos.ListaApartamentos

class ApartamentoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val binding = ApartamentoLayoutBinding.bind(view)
     var liked:Boolean = false
    fun renderizar (apartamento: Apartamento) {
        binding.tvNombreApartamento.text = apartamento.nombreApartamento
        binding.tvDireccionApartamento.text = apartamento.direccionApartamento
        binding.tvPrecioApartamento.text = apartamento.precioApartamento
        binding.tvPropietario.text = apartamento.nombreUsuario






    }

    fun color(liked: Boolean){
        if (liked){
            binding.btnLike.setImageResource(R.drawable.ic_baseline_favorite_24)
        }
        else{
            binding.btnLike.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

    fun like(likes:MutableList<String>, liked:Boolean, apartamento: Apartamento){
        this.liked = liked
        color(liked)
        binding.btnLike.setOnClickListener{
            this.liked = !liked
            color(this.liked)

            if (this.liked){
                likes.add(auth.uid!!)
            }
            else{
                likes.remove(auth.uid!!)
            }
            val doc = db.collection("apartamentos").document(apartamento.uid!!)
            db.runTransaction{
                it.update(doc, "likes", likes)
                null
            }
        }
        binding.tvLikesCount.text = likes.size.toString()
    }

}