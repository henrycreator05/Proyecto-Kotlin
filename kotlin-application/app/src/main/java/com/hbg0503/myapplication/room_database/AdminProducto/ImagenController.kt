package com.hbg0503.myapplication.room_database.AdminProducto

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

object ImagenController {
    fun selectPhoneFromGallery(activity: Activity,code:Int){
        val inten = Intent(Intent(Intent.ACTION_PICK))
        inten.type="image/*"
        activity.startActivityForResult(inten,code)

    }
    fun saveImagen(context: Context,id:Long,uri: Uri){

        val file = File(context.filesDir,id.toString())
        val bytes =context.contentResolver.openInputStream(uri)?.readBytes()!!
        file.writeBytes(bytes)
        //codigo para la imagen firebase
        val Folder: StorageReference =
            FirebaseStorage.getInstance().getReference().child("imagen")
        val file_name = Folder.child(id.toString())
        file_name.putFile(uri!!).addOnSuccessListener { taskSnapshot ->
            file_name.getDownloadUrl().addOnCompleteListener { uri ->
                val hashMap =
                    HashMap<String, String>()
                hashMap["link"] = java.lang.String.valueOf(uri)
                Log.d("Mensaje", "se subio correctamente")
            }
        }
    }
}