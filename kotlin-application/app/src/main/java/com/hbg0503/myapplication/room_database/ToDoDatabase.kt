package com.hbg0503.myapplication.room_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hbg0503.myapplication.room_database.AdminProducto.Producto

@Suppress("unused")
@Database(entities = arrayOf(ToDo::class), version = 1)
abstract class ToDoDatabase : RoomDatabase(){
    abstract fun todoDao() : ToDoDAD
    companion object{
        @Volatile
        private var INSTANCE : ToDoDatabase?=null
        fun getDataBase(context: Context): ToDoDatabase{
            return INSTANCE ?: synchronized(this){
                val instence = Room.databaseBuilder(context.applicationContext,
                ToDoDatabase::class.java,
                "ToDoDatabasesG").build()
                INSTANCE = instence
                instence
            }
        }
    }
}