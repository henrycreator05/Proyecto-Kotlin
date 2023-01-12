package com.hbg0503.myapplication

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.hbg0503.myapplication.room_database.ToDo
import com.hbg0503.myapplication.room_database.ToDoDatabase
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DetailFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmento = inflater.inflate(R.layout.fragment_detail, container, false)
        var tarea = requireArguments().getString("tarea")
        var hora = requireArguments().getString("hora")
        var lugar = requireArguments().getString("lugar")
        var id = requireArguments().getString("id")
        var textViewTarea: TextView = fragmento.findViewById(R.id.tvTarea)
        var tvHora: TextView = fragmento.findViewById(R.id.tvHora)
        var tvLugar: TextView = fragmento.findViewById(R.id.tvLugar)
        var tvID: TextView = fragmento.findViewById(R.id.tvID)
        textViewTarea.text = tarea
        tvHora.text = hora
        tvLugar.text = lugar
        tvID.text = id

        val btnEditar: Button = fragmento.findViewById(R.id.btEditFD)
        btnEditar.setOnClickListener {
            val principal = Intent(inflater.context, NewTaskActivity::class.java)
            principal.putExtra("tarea", textViewTarea.text as String)
            principal.putExtra("hora", tvHora.text as String)
            principal.putExtra("lugar", tvLugar.text as String)
            principal.putExtra("id", tvID.text as String)
            startActivity(principal)
        }

        val btnDelete: Button = fragmento.findViewById(R.id.btDeleteFD)
        btnDelete.setOnClickListener {

            val positiveButton = { dialogpositivo: DialogInterface, which: Int ->
                val db = ToDoDatabase.getDataBase(requireActivity())
                val todoDao = db.todoDao()
                var idTask: String = tvID.text.toString()
                val task = ToDo(idTask.toInt(), tarea.toString(), hora.toString(), lugar.toString())
                runBlocking {
                    launch {
                        todoDao.deleteTask(task)
                    }
                }

                val actividadtodoActiviti = Intent(inflater.context, ToDoMainActivity::class.java)
                startActivity(actividadtodoActiviti)

            }
            val negativeButton = { _: DialogInterface, _: Int -> }
            val dialogpositivo = AlertDialog.Builder(requireActivity())
                .setTitle(getString(R.string.text_welcome))
                .setMessage("Task=" + tarea.toString())
                .setPositiveButton("ok", positiveButton)
                .setNegativeButton("cancelar", negativeButton)
                .create()
                .show()
        }

        val btnconsultar: Button = fragmento.findViewById(R.id.bnconsultarFD)
        btnconsultar.setOnClickListener {
            var taskC: ToDo? = null

            val db = ToDoDatabase.getDataBase(requireActivity())
            val todoDao = db.todoDao()
            var idTask: String = tvID.text.toString()

            runBlocking {
                launch {
                    taskC = todoDao.getTask()


                }
            }

        Toast.makeText(requireActivity(), taskC?.title, Toast.LENGTH_LONG).show()

        }


        return fragmento
    }
}