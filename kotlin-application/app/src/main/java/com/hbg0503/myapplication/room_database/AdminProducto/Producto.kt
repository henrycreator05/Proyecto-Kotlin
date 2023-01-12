package com.hbg0503.myapplication.room_database.AdminProducto
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity(tableName = "productos")
class Producto(
    @PrimaryKey(autoGenerate = true)
    var idProducto: Int=0,
    val nombre : String,
    val precio : Float,
    val descripcion : String,
    val imagen: Int
        ) : Serializable
