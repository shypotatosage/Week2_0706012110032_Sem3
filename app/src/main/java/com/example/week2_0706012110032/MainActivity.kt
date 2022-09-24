package com.example.week2_0706012110032

import Adapter.RVAdapter
import Database.GlobalVar
import Interface.CardListener
import Model.Animal
import Model.Ayam
import Model.Kambing
import Model.Sapi
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CardListener {

    private val adapter = RVAdapter(GlobalVar.listDataAnimal, this)
    private lateinit var filter: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        filter = "hewan"
        adapter.listAnimal = GlobalVar.listDataAnimal
        setUpRecyclerView()
        listener()
    }

    override fun onResume() {
        super.onResume()

        if (adapter.listAnimal.size > 0) {
            if (adapter.listAnimal == GlobalVar.listDataTemp) {
                if (adapter.listAnimal[0] is Sapi) {
                    filter("Sapi")
                    adapter.listAnimal = GlobalVar.listDataTemp
                } else if (adapter.listAnimal[0] is Ayam) {
                    filter("Ayam")
                    adapter.listAnimal = GlobalVar.listDataTemp
                } else {
                    filter("Kambing")
                    adapter.listAnimal = GlobalVar.listDataTemp
                }
            } else {
                setUp("hewan")
                adapter.listAnimal = GlobalVar.listDataAnimal
            }
        } else {
            setUp(filter)

            if (filter == "hewan") {
                adapter.listAnimal = GlobalVar.listDataAnimal
            } else {
                adapter.listAnimal = GlobalVar.listDataTemp
            }
        }

        setUpRecyclerView()
        adapter.notifyDataSetChanged()
    }

    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(this, v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            if (menuItem.itemId == R.id.option_1) {
                filter("Sapi")
            } else if (menuItem.itemId == R.id.option_2) {
                filter("Ayam")
            } else if (menuItem.itemId == R.id.option_3) {
                filter("Kambing")
            }

            if (menuItem.itemId == R.id.option_4) {
                adapter.listAnimal = GlobalVar.listDataAnimal
            } else {
                adapter.listAnimal = GlobalVar.listDataTemp
            }

            setUpRecyclerView()
            adapter.notifyDataSetChanged()

            if (menuItem.itemId == R.id.option_1) {
                setUp("Sapi")
            } else if (menuItem.itemId == R.id.option_2) {
                setUp("Ayam")
            } else if (menuItem.itemId == R.id.option_3) {
                setUp("Kambing")
            } else {
                setUp("hewan")
            }

            true
        }
        popup.setOnDismissListener {
            // Respond to popup being dismissed.
        }
        // Show the popup menu.
        popup.show()
    }

    private fun listener() {
        addUpdateAnimal.setOnClickListener {
            val myIntent = Intent(this, AddActivity::class.java)

            startActivity(myIntent)
        }

        menuBtn.setOnClickListener {
            showMenu(it, R.menu.popup_menu)
        }
    }

    private fun setUpRecyclerView() {
        val layoutManager = LinearLayoutManager(baseContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    override fun onEditClick(position: Int) {
        if (adapter.listAnimal == GlobalVar.listDataTemp) {
            val myIntent = Intent(this, AddActivity::class.java).apply {
                putExtra("Position1", GlobalVar.listDataAnimal.indexOf(adapter.listAnimal.get(position)))
                putExtra("Position2", position)
            }

            startActivity(myIntent)
        } else {
            val myIntent = Intent(this, AddActivity::class.java).apply {
                putExtra("Position1", position)
            }

            startActivity(myIntent)
        }
    }

    override fun onDeleteClick(position: Int, data: Animal) {
        MaterialAlertDialogBuilder(
            this,
            com.google.android.material.R.style.MaterialAlertDialog_Material3
        )
            .setTitle(resources.getString(R.string.title))
            .setMessage(resources.getString(R.string.supporting_text))
            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, which ->
                dialog.cancel()
            }
            .setPositiveButton(resources.getString(R.string.delete)) { dialog, which ->
                if (adapter.listAnimal == GlobalVar.listDataTemp) {
                    GlobalVar.listDataTemp.remove(data)
                }

                GlobalVar.listDataAnimal.removeAt(position)

                Toast.makeText(applicationContext, "Berhasil menghapus data hewan", Toast.LENGTH_SHORT).show()

                setUp("hewan")
                adapter.notifyDataSetChanged()

                dialog.cancel()
            }
            .show()
    }

    private fun setUp(str: String) {
        if (adapter.listAnimal.size != 0) {
            noAnimals_ImageView.visibility = View.INVISIBLE
            noAnimals_TextView.visibility = View.INVISIBLE
        } else {
            noAnimals_ImageView.visibility = View.VISIBLE
            noAnimals_TextView.visibility = View.VISIBLE
            noAnimals_TextView.text = "Anda belum memiliki " + str
        }
    }

    private fun filter(type: String) {
        GlobalVar.listDataTemp.clear()

        if (type == "Sapi"){
            for (x in GlobalVar.listDataAnimal) {
                if (x is Sapi) {
                    GlobalVar.listDataTemp.add(x)
                }
            }
        } else if (type == "Ayam") {
            for (x in GlobalVar.listDataAnimal) {
                if (x is Ayam) {
                    GlobalVar.listDataTemp.add(x)
                }
            }
        } else {
            for (x in GlobalVar.listDataAnimal) {
                if (x is Kambing) {
                    GlobalVar.listDataTemp.add(x)
                }
            }
        }
    }
}