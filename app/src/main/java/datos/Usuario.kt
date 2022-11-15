package datos

import com.google.firebase.firestore.Exclude

data class Usuario (
    val nombre : String?,
    val apellido : String?,
    var username : String?,
    val email:String?,
){
    @Exclude
    @set:Exclude
    @get:Exclude
    var uid:String? = null

    constructor():this(null, null, null, null)
}