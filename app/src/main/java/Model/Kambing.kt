package Model

class Kambing(name: String, age: Int, imageUri: String) : Animal(name, age, imageUri) {
    override fun interaction(): String {
        super.interaction()
        var msg = "Mbeeeee~ Mbeeeee~"

        return msg;
    }

    override fun feed(): String {
        super.feed()
        var msg = "Kamu memberi makan hewanmu dengan rumput"

        return msg;
    }
}