package com.hbg0503.myapplication

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : AppCompatActivity() {
    private val GOOGLE_SING_IN = 100
    private var btnGoogle: SignInButton?=null
    private var edtUsername: EditText?=null
    private var edtPassword: EditText?=null
    private var authLayout: LinearLayout?=null
    private var textViewforgotPWD : TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.my_toobar))
        edtUsername = findViewById(R.id.edtUsername)
        edtPassword = findViewById(R.id.edtPassword)
        authLayout = findViewById(R.id.authLayout)
        btnGoogle = findViewById(R.id.btnGoogle)
        textViewforgotPWD= findViewById(R.id.textViewForgotPWD)
        session()
        loginGoogle()
        textViewforgotPWD!!.setOnClickListener {
            val intent = Intent(this,ForgotPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

     fun loginGoogle() {
         btnGoogle!!.setOnClickListener {
             val googleleconf : GoogleSignInOptions=GoogleSignInOptions
                 .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                 .requestIdToken(getString(R.string.default_web_client_id))
                 .requestEmail()
                 .build()
             val googleClient : GoogleSignInClient=GoogleSignIn
                 .getClient(this,googleleconf)
             googleClient.signOut()
             startActivityForResult(googleClient.signInIntent,GOOGLE_SING_IN)
         }
    }

    fun onlogin(botonLogin: View) {
            var messageerrorUsername= getString(R.string.messageerrorUsername)
            var username:String=edtUsername!!.text.toString()
        if (username=="gerard@correo.co")
        {
            if (edtPassword!!.text.toString()=="1234")
            {
                val positiveButton= { dialogpositivo: DialogInterface, which: Int ->
                    val intent = Intent(this, WelcomeActivity::class.java)
                    startActivity(intent)
                }
                val negativeButton={_:DialogInterface,_:Int->}
                val dialogpositivo= AlertDialog.Builder(this)
                    .setTitle(getString(R.string.text_welcome))
                    .setMessage(getString(R.string.textemail)+username)
                    .setPositiveButton("ok",positiveButton)
                    .setNegativeButton("cancelar",negativeButton)
                    .create()
                    .show()
            }
            else {
                /*
                val dialog = AlertDialog.Builder(this).setTitle("ERROR!").setMessage("invalide password").create().show()

                 */
                Toast.makeText(this,"invalid password",Toast.LENGTH_LONG).show()
            }
        }
        else{
            val dialog= AlertDialog.Builder(this).setTitle("ERROR!").setMessage(messageerrorUsername).create().show()
            }
        }

    fun onRegister(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    fun onRegisterEmail(view: View) {
        title="Autentification"
        if(edtUsername!!.text.isNotEmpty() && edtPassword!!.text.isNotEmpty()){
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                edtUsername!!.text.toString(),
                edtPassword!!.text.toString()
            ).addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(this,"Correcto",Toast.LENGTH_SHORT).show()
                    showHome(it.result?.user?.email?:"",ProviderType.BASIC)
                }
                else{
                    showAlert()
                }
            }

        }
    }
    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("se ha producido un error autentificando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog : AlertDialog= builder.create()
        dialog.show()
    }
    private fun showHome(email : String, provider : ProviderType){
        val homeIntent = Intent(this,WelcomeActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }

    fun onLoginEmail(view: View) {
        title="Autentification"
        if(edtUsername!!.text.isNotEmpty() && edtPassword!!.text.isNotEmpty()){
            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                edtUsername!!.text.toString(),
                edtPassword!!.text.toString()
            ).addOnCompleteListener {
                if(it.isSuccessful){
                    showHome(it.result?.user?.email?:"",ProviderType.BASIC)
                }else{
                    showAlert()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        authLayout!!.visibility= View.VISIBLE

    }
    private fun session(){
        val prefs : SharedPreferences = getSharedPreferences(
            getString(R.string.prefs_file),Context.MODE_PRIVATE)
        val email: String? = prefs.getString("provider", null)
        val provider: String? = prefs.getString("provider", null)
        if(email !=null && provider !=null){
            authLayout!!.visibility=View.INVISIBLE
            showHome(email,ProviderType.valueOf(provider))
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val prefs = getSharedPreferences(getString(R.string.prefs_file),Context.MODE_PRIVATE).edit()
        prefs.clear()
        prefs.apply()
        FirebaseAuth.getInstance().signOut()
        onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val task : Task<GoogleSignInAccount> = GoogleSignIn
            .getSignedInAccountFromIntent(data)
        try {
            val account : GoogleSignInAccount? = task.getResult(ApiException::class.java)
            if(account != null){
                val credential : AuthCredential= GoogleAuthProvider
                    .getCredential(account.idToken,null)
                FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                    if(it.isSuccessful) {
                        showHome(account.email ?: "", ProviderType.GOOGLE)
                    }else{
                        showAlert()
                    }
                }
            }
        }catch (e: ApiException){
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Error")
            builder.setMessage(e.toString())
            builder.setPositiveButton("Aceptar", null)
            val dialog : AlertDialog= builder.create()
            dialog.show()
        }
    }

}