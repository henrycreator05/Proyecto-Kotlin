package com.hbg0503.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.hbg0503.myapplication.room_database.ToDoDatabase
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.Unit as Unit1

class ToDoFragment : Fragment() {
    private lateinit var listRecyclerView: RecyclerView
    private lateinit var myAdapter: RecyclerView.Adapter<MyTaskListAdapter.MyViewHolder>
    var myTaskTitles: ArrayList<String> = ArrayList()
    var myTaskTimes: ArrayList<String> = ArrayList()
    var myTaskPlaces: ArrayList<String> = ArrayList()
    var myTaskIds: ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmento=inflater.inflate(R.layout.fragment_to_do,container,false)
        /* val detail1 : Button=fragmento.findViewById(R.id.btn_detail_1)
           val detail2 : Button=fragmento.findViewById(R.id.btn_detail_2)
           val detail3 : Button=fragmento.findViewById(R.id.btn_detail_3)
           detail1.setOnClickListener(View.OnClickListener{
               val  datos = Bundle()
                  datos.putString("tarea","ir al super mercado")
                  datos.putString("hora","7:21")
                  datos.putString("lugar","superx")
               activity?.getSupportFragmentManager()?.beginTransaction()
                   ?.setReorderingAllowed(true)
                   ?.replace(R.id.fcv,DetailFragment::class.java,datos,"detail")
                   ?.addToBackStack("")
                   ?.commit()
               })*/
        return fragmento
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*  myTaskTitles.add("ir al super mercado")
          myTaskTitles.add("llevar el carro  al a tecnomecanica")
          myTaskTitles.add("cita medica")
          myTaskTitles.add("tarea 4")
          myTaskTitles.add("tarea 5")
          myTaskTitles.add("tarea 6")
          myTaskTitles.add("tarea 7")
          myTaskTitles.add("tarea 8")

          myTaskTimes.add("10:08 pm")
          myTaskTimes.add("12:00 am")
          myTaskTimes.add("11:00 am")
          myTaskTimes.add("2:00 pm")
          myTaskTimes.add("10:08 pm")
          myTaskTimes.add("12:00 am")
          myTaskTimes.add("11:00 am")
          myTaskTimes.add("2:00 pm")

          myTaskPlaces.add("lugar 1")
          myTaskPlaces.add("lugar 2")
          myTaskPlaces.add("lugar 3")
          myTaskPlaces.add("lugar 4")
          myTaskPlaces.add("lugar 5")
          myTaskPlaces.add("lugar 6")
          myTaskPlaces.add("lugar 7")
          myTaskPlaces.add("lugar 8")
          var info : Bundle = Bundle()
          info.putStringArrayList("titles",myTaskTitles)
          info.putStringArrayList("times",myTaskTimes)
          info.putStringArrayList("places",myTaskPlaces)
          listRecyclerView= requireView().findViewById(R.id.recyclerToDoList)
          myAdapter = MyTaskListAdapter(activity as AppCompatActivity,info)
          listRecyclerView.setHasFixedSize(true)
          listRecyclerView.adapter = myAdapter
          listRecyclerView.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))*/
        val fab: View = requireActivity().findViewById(R.id.fab2)
        fab.setOnClickListener { view ->
            val intent = Intent(activity, NewTaskActivity::class.java)
            var recursiveScope = 0
            startActivityForResult(intent, recursiveScope)
        }
        var info : Bundle = Bundle()
        info.putStringArrayList("titles",myTaskTitles)
        info.putStringArrayList("times",myTaskTimes)
        info.putStringArrayList("places",myTaskPlaces)
        info.putStringArrayList("ids",myTaskIds)
        listRecyclerView= requireView().findViewById(R.id.recyclerToDoList)
        myAdapter = MyTaskListAdapter(activity as AppCompatActivity,info)
        listRecyclerView.setHasFixedSize(true)
        listRecyclerView.adapter = myAdapter
        listRecyclerView.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))
        updateList()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 0) {
            if(resultCode==Activity.RESULT_OK){
                updateList()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
    fun updateList(){
        val  db= ToDoDatabase.getDataBase(requireActivity())
        val toDoDAD = db.todoDao()
        runBlocking {
            launch {
                var result = toDoDAD.getAllTasks()
                var i=0
                myTaskTitles.clear()
                myTaskTimes.clear()
                myTaskPlaces.clear()
                myTaskIds.clear()
                while (i<result.size){
                    myTaskTitles.add(result[i].title.toString())
                    myTaskTimes.add(result[i].time.toString())
                    myTaskPlaces.add(result[i].place.toString())
                    myTaskIds.add(result[i].id.toString())
                    i++
                }
                myAdapter.notifyDataSetChanged()

            }
        }
    }
}