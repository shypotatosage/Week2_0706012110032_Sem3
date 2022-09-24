package Interface

import Model.Animal

interface CardListener {

    fun onEditClick(position: Int) {

    }

    fun onDeleteClick(position: Int, data: Animal) {

    }


}