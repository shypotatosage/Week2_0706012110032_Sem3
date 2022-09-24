package Model

class Sapi(name: String, age: Int, imageUri: String) : Animal(name, age, imageUri) {
    override fun interaction(): String {
        var msg = "Moooooooo~"

        return msg;
    }
}