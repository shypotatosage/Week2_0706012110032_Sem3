package com.example.week2_0706012110032

import Database.GlobalVar
import Model.Animal
import Model.Ayam
import Model.Kambing
import Model.Sapi
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.card_animal.*

class AddActivity : AppCompatActivity() {

    private var position1 = -1
    private var position2 = -1
    private lateinit var urii: String

    private val GetResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){   // APLIKASI GALLERY SUKSES MENDAPATKAN IMAGE
            val uri = it.data?.data
            urii = uri.toString()// GET PATH TO IMAGE FROM GALLEY

            addUpdateAnimal_ImageView.setImageURI(uri)  // MENAMPILKAN DI IMAGE VIEW
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        urii = ""

        listener()
        GetIntent()
        display()
    }

    private fun listener() {
        radioError.visibility = View.GONE

        addUpdateAnimal_ImageView.setOnClickListener{
            val myIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            myIntent.type = "image/*"
            GetResult.launch(myIntent)
        }

        addUpdateAnimalBackButton.setOnClickListener {
            finish()
        }

        addUpdateAnimalButton.setOnClickListener {
            var isCompleted = true

            if (animalNameTIL.editText?.text.toString().trim() == "") {
                isCompleted = false
                animalNameTIL.error = "Tolong isi kolom nama hewan"
            } else {
                animalNameTIL.error = ""
            }

            if (animalTypeRG.checkedRadioButtonId == -1) {
                isCompleted = false
                radioError.visibility = View.VISIBLE
            } else {
                radioError.visibility = View.GONE
            }

            if (animalAgeTIL.editText?.text.toString().trim() == "") {
                isCompleted = false
                animalAgeTIL.error = "Tolong isi kolom nama hewan"
            } else {
                animalAgeTIL.error = ""
            }

            if (isCompleted) {
                var animal: Animal

                if (animalTypeRG.checkedRadioButtonId == R.id.radioButton) {
                    animal = Sapi(animalNameTIL.editText?.text.toString().trim(), animalAgeTIL.editText?.text.toString().trim().toInt(), "")
                } else if (animalTypeRG.checkedRadioButtonId == R.id.radioButton2){
                    animal = Ayam(animalNameTIL.editText?.text.toString().trim(), animalAgeTIL.editText?.text.toString().trim().toInt(), "")
                } else {
                    animal = Kambing(animalNameTIL.editText?.text.toString().trim(), animalAgeTIL.editText?.text.toString().trim().toInt(), "")
                }

                if (position1 == -1) {
                    GlobalVar.listDataAnimal.add(animal)

                    var index = GlobalVar.listDataAnimal.size - 1

                    if (urii.isNotEmpty()) {
                        GlobalVar.listDataAnimal[index].imageUri = urii
                    }

                    Toast.makeText(applicationContext, "Berhasil menambahkan data hewan", Toast.LENGTH_SHORT).show()
                } else {
                    animal.imageUri = GlobalVar.listDataAnimal[position1].imageUri

                    GlobalVar.listDataAnimal.set(position1, animal)

                    if (urii.isNotEmpty()) {
                        GlobalVar.listDataAnimal[position1].imageUri = urii
                    }

                    if (position2 != -1) {
                        if ((GlobalVar.listDataTemp[position2] is Sapi && animal is Sapi) || (GlobalVar.listDataTemp[position2] is Ayam && animal is Ayam) || (GlobalVar.listDataTemp[position2] is Kambing && animal is Kambing)) {
                            GlobalVar.listDataTemp.set(position2, animal)
                        } else {
                            GlobalVar.listDataTemp.removeAt(position2)
                        }

                        if (urii.isNotEmpty()) {
                            GlobalVar.listDataTemp[position2].imageUri = urii
                        }
                    }

                    Toast.makeText(applicationContext, "Berhasil memperbarui data hewan", Toast.LENGTH_SHORT).show()
                }1

                finish()
            }
        }
    }

    private fun GetIntent() {
        position1 = intent.getIntExtra("Position1", -1)
        position2 = intent.getIntExtra("Position2", -1)
    }

    private fun display() {
        if (position1 != -1) {
            animalNameTIL.editText!!.setText(GlobalVar.listDataAnimal.get(position1).name)

            if (GlobalVar.listDataAnimal.get(position1) is Sapi) {
                animalTypeRG.check(R.id.radioButton)
            } else if (GlobalVar.listDataAnimal.get(position1) is Ayam) {
                animalTypeRG.check(R.id.radioButton2)
            } else {
                animalTypeRG.check(R.id.radioButton3)
            }

            animalAgeTIL.editText!!.setText(GlobalVar.listDataAnimal.get(position1).age.toString())

             if (GlobalVar.listDataAnimal.get(position1).imageUri != "") {
                addUpdateAnimal_ImageView.setImageURI(
                    Uri.parse(
                        GlobalVar.listDataAnimal.get(
                            position1
                        ).imageUri
                    )
                )
            }

            addUpdateHewanTV.text = "Edit Hewan"
        }
    }
}