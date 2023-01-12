package com.hbg0503.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {
    private var editEmailForget : EditText?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        editEmailForget= findViewById(R.id.editEmailForget)
    }

    fun ToForget(view: View) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(editEmailForget!!.text.toString())
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    Toast.makeText(this, "correo enviado", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this,"correo no valido",Toast.LENGTH_LONG).show()
                }
            }
    }
}