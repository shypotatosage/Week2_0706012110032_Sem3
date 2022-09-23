package Adapter

import Database.GlobalVar
import Interface.CardListener
import Model.Animal
import Model.Ayam
import Model.Sapi
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.week2_0706012110032.R
import kotlinx.android.synthetic.main.card_animal.view.*

class RVAdapter(var listAnimal: ArrayList<Animal>, val cardListener: CardListener):
    RecyclerView.Adapter<RVAdapter.viewHolder>(){
    class viewHolder (itemView: View, val cardListener1: CardListener): RecyclerView.ViewHolder(itemView){

        fun setData(data: Animal){
            itemView.animalName_TextView.text = data.name

            if (data is Sapi) {
                itemView.animalType_TextView.text = "Sapi"
            } else if (data is Ayam) {
                itemView.animalType_TextView.text = "Ayam"
            } else {
                itemView.animalType_TextView.text = "Kambing"
            }

            itemView.animalAge_TextView.text = "Usia : " + data.age.toString() + " Tahun"

            if (data.imageUri.isNotEmpty()) {
                itemView.animal_ImageView.setImageURI(Uri.parse(data.imageUri))
            }

            itemView.editButton.setOnClickListener {
                cardListener1.onEditClick(adapterPosition)
            }

            itemView.deleteButton.setOnClickListener {
                cardListener1.onDeleteClick(adapterPosition)
            }

            itemView.interactBtn.setOnClickListener {
                Toast.makeText(itemView.context, data.interaction(), Toast.LENGTH_SHORT).show()
            }

            itemView.feedBtn.setOnClickListener {
                Toast.makeText(itemView.context, data.feed(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RVAdapter.viewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.card_animal, parent, false)
        return viewHolder(view, cardListener)
    }

    override fun onBindViewHolder(holder: RVAdapter.viewHolder, position: Int) {
        holder.setData(listAnimal[position])
    }

    override fun getItemCount(): Int {
        return listAnimal.size
    }

}