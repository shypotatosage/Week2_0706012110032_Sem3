package Model

import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class Ayam(name: String, age: Int, imageUri: String) : Animal(name, age, imageUri) {
    override fun interaction(): String {
        var msg = "Pok Pok Pok..."

        return msg;
    }
}