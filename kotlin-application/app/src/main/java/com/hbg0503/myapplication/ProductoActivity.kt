package com.hbg0503.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.firebase.firestore.FirebaseFirestore
import com.hbg0503.myapplication.room_database.AdminProducto.Producto
import com.hbg0503.myapplication.room_database.AdminProducto.ProductoDatabase
import kotlinx.android.synthetic.main.activity_producto.*
import kotlinx.android.synthetic.main.item_producto.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producto)

        val producto = intent.getSerializableExtra("producto") as Producto
        textView_NombrePA.text=producto.nombre
        textView_PrecioPA.text="${producto.precio}"
        textView_DetallePA.text=producto.descripcion
        imageViewAP.setImageResource(producto.imagen)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.producto_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val producto = intent.getSerializableExtra("producto") as Producto
        val database = ProductoDatabase.getDatabase(this)
        val dbFirebase = FirebaseFirestore.getInstance()
        when (item.itemId) {
            R.id.edit_item -> {
                val intent = Intent(this, NuevoProductoActivity::class.java)
                intent.putExtra("producto", producto)
                startActivity(intent)
            }
            R.id.delete_item -> {
                CoroutineScope(Dispatchers.IO).launch {
                    database.productos().delete(producto)
                    dbFirebase.collection("Productos")
                        .document(producto.idProducto.toString()).delete()
                    this@ProductoActivity.finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun onEditarP(view: View) {
        val producto = intent.getSerializableExtra("producto") as Producto
        val intent = Intent(this, NuevoProductoActivity::class.java)
        intent.putExtra("producto", producto)
        startActivity(intent)
    }
}