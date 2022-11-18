package datos

import com.google.firebase.firestore.Exclude

data class Apartamento (
    val nombreApartamento : String?,
    val direccionApartamento : String?,
    val precioApartamento : String?,
    val nombreUsuario:String?,
    val imageUrl:String?,
    val propId:String?,
    val likes:ArrayList<String>? = arrayListOf(),


){
    @Exclude
    @set:Exclude
    @get:Exclude
    var uid:String? = null

    constructor():this(null, null, null, null, null, null, null)

}