package Model

class Kambing(name: String, age: Int, imageUri: String) : Animal(name, age, imageUri) {
    override fun interaction(): String {
        var msg = "Mbeeeee~ Mbeeeee~"

        return msg;
    }
}