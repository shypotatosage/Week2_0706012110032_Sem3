package Model

import android.os.Parcel
import android.os.Parcelable

open class Animal (
    var name: String,
    var age: Int,
    var imageUri: String
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!
    ) {
    }

    open fun interaction(): String {
        return "a";
    }

    open fun feed(ayam: String): String {
        return "Kamu memberi makan hewanmu dengan biji jagung";
    }

    open fun feed(sapiKambing: Int): String {
        return "Kamu memberi makan hewanmu dengan rumput";
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(age)
        parcel.writeString(imageUri)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Animal> {
        override fun createFromParcel(parcel: Parcel): Animal {
            return Animal(parcel)
        }

        override fun newArray(size: Int): Array<Animal?> {
            return arrayOfNulls(size)
        }
    }

}