package com.hbg0503.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.regex.Pattern

@SuppressLint("ParcelCreator")
class RegisterActivity() : AppCompatActivity(){
    private var editName: EditText? = null
    private var editLastName: EditText? = null
    private var editNit: EditText? = null
    private var editPhone: EditText? = null
    private var editEmail: EditText? = null
    private var editPassword: EditText? = null
    private var checkBoxPolicies: CheckBox? = null
    private var nameAndLast_Pattern: Pattern = Pattern.compile(
        "[a-zA-Z]*"
    )
    private val password_Pattern: Pattern = Pattern.compile(
        "^" +
                "(?=.*[0-9])" +
                "(?=.*[a-z])" +
                "(?=.*[A-Z])" +
                "(?=.*[(@#%^&+=.])" +
                ".{8,}" +
                "$"
    )
    private  val email_Pattern : Pattern= Patterns.EMAIL_ADDRESS
        private val phone_Pattern : Pattern= Patterns.PHONE

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        editName = findViewById(R.id.editName)
        editLastName = findViewById(R.id.editLastName)
        editNit = findViewById(R.id.editNit)
        editPhone = findViewById(R.id.editPhone)
        editEmail = findViewById(R.id.editEmail)
        editPassword = findViewById(R.id.editPassword)
        checkBoxPolicies = findViewById(R.id.CheckboxPolicies)
    }
    fun onRegisterSave(view: View) {
        if (validateForm()) {
            Toast.makeText(this, "Correcto", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
        }

    }
    private fun validateForm(): Boolean {
        var validate= true
        var name: String=editName!!.text.toString()
        var lastName:String= editLastName!!.text.toString()
        var nit: String= editNit!!.text.toString()
        var password:String=editPassword!!.text.toString()
        var email: String = editEmail!!.text.toString()
        var phone: String = editPhone!!.text.toString()

        /* checkBox Policies*/
        if (!checkBoxPolicies!!.isChecked)
        {
            validate = false
        }

        /*bloque name*/
        if (TextUtils.isEmpty(name))
        {
            editName!!.error = "Requerido"
            validate = false
        }else if(!nameAndLast_Pattern.matcher(name.replace(" ","")).matches())
        {
            editName!!.error = "nombre no valido"
            validate = false
        } else editName!!.error = null

        /*bloque password*/

        if (TextUtils.isEmpty(password))
        {
            editPassword!!.error = "Requerido"
            validate = false
        } else if (!password_Pattern.matcher(password).matches())
        {
            editPassword!!.error = "contraseña no  valido"
            validate = false
        } else editPassword!!.error = null

        /*bloque Email*/
        if (TextUtils.isEmpty(email))
        {
            editEmail!!.error = "Requerido"
            validate = false
        } else if (!email_Pattern.matcher(email).matches())
        {
            editEmail!!.error = "email no valido"
            validate = false
        } else editEmail!!.error = null

       /* Bloque Phone*/
        if (TextUtils.isEmpty(phone))
        {
            editPhone!!.error = "Requerido"
            validate = false
        } else if (!phone_Pattern.matcher(phone).matches())
        {
            editPhone!!.error = "email no valido"
            validate = false
        } else editPhone!!.error = null

        /*bloque lastName*/
        if (TextUtils.isEmpty(lastName))
        {
            editLastName!!.error = "Requerido"
            validate = false
        }else if(!nameAndLast_Pattern.matcher(lastName.replace(" ","")).matches())
        {
            editLastName!!.error = "nombre no valido"
            validate = false
        } else editLastName!!.error = null

        /*bloque nit*/
        if (TextUtils.isEmpty(nit))
        {
            editNit!!.error = "Requerido"
            validate = false
        }else editNit!!.error=null
            return validate
    }
}
