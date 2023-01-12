package com.hbg0503.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.hbg0503.myapplication.room_database.AdminProducto.Producto
import com.hbg0503.myapplication.room_database.AdminProducto.ProductoAdactar
import com.hbg0503.myapplication.room_database.AdminProducto.ProductoDatabase
import kotlinx.android.synthetic.main.activity_lista_productos.*

class ListaProductosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_productos)
        //val producto = Producto(1, "tv",500.0F,"tv hdmi",R.drawable.ic_baseline_tv_24)
        //val producto2 = Producto(2, "camera",300.0F,"12mp",R.drawable.ic_baseline_photo_camera_24)
        //val listaProductos= listOf(producto,producto2)
        var listaProductos = emptyList<Producto>()
        val database = ProductoDatabase.getDatabase(this)
        database.productos().getAll().observe(
            this, Observer {
                listaProductos = it
                val adactar = ProductoAdactar(this, listaProductos)
                lista.adapter = adactar
            }
        )
        //val adactar =ProductoAdactar(this,listaProductos)
        //lista.adapter = adactar
        lista.setOnItemClickListener() { parent, view, position, id ->
            val intent = Intent(this, ProductoActivity::class.java)
            intent.putExtra("producto", listaProductos[position])
            startActivity(intent)
        }
        floatingacgtionbittonLPA.setOnClickListener {
            val intent = Intent(this, NuevoProductoActivity::class.java)
            startActivity(intent)
        }
    }
}