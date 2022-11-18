package adapter

import android.app.AlertDialog
import android.content.DialogInterface
import android.text.Layout
import android.view.View
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.fora_neo.R
import com.example.fora_neo.databinding.ApartamentoLayoutBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import datos.Apartamento
import datos.ListaApartamentos

class ApartamentoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val auth = FirebaseAuth.getInstance()
    private var storageApartPicRef = FirebaseStorage.getInstance().reference.child("pics")
    val db = FirebaseFirestore.getInstance()
    val binding = ApartamentoLayoutBinding.bind(view)
     var liked:Boolean = false
    fun renderizar (apartamento: Apartamento, context:FragmentActivity?) {
        binding.tvNombreApartamento.text = apartamento.nombreApartamento
        binding.tvDireccionApartamento.text = apartamento.direccionApartamento
        binding.tvPrecioApartamento.text = apartamento.precioApartamento
        binding.tvPropietario.text = apartamento.nombreUsuario

        if(apartamento.propId == auth.currentUser!!.uid){
            binding.btnDelete.visibility = View.VISIBLE
        }

        binding.btnDelete.setOnClickListener {

            AlertDialog.Builder(context).apply {
                setTitle("Cuenta creada")
                setMessage("Seguro que quiere eliminarlo?")
                setPositiveButton("Aceptar"){ _: DialogInterface, _: Int ->
                    db.collection("apartamentos").document(apartamento.uid!!).delete().addOnSuccessListener {
                        val fileRef = storageApartPicRef!!.child("${apartamento.uid}.jpg")
                        fileRef.delete().addOnSuccessListener {
                            Toast.makeText(context, "Eliminado", Toast.LENGTH_SHORT).show()
                        }

                    }

                }
                setNegativeButton("Cancelar"){ _:DialogInterface, _:Int ->

                }
            }.show()


        }


        Picasso.get()
            .load(apartamento.imageUrl)
            .error(R.mipmap.ic_launcher_round)
            .into(binding.ivApartamento)

        //binding.ivApartamento.setImageURI(apartamento.imageUrl!!.toUri())
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