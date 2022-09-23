package Model

import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class Ayam(name: String, age: Int, imageUri: String) : Animal(name, age, imageUri) {
    override fun interaction(): String {
        super.interaction()
        var msg = "Pok Pok Pok..."

        return msg;
    }

    override fun feed(): String {
        super.feed()
        var msg = "Kamu memberi makan hewanmu dengan biji jagung"

        return msg;
    }
}