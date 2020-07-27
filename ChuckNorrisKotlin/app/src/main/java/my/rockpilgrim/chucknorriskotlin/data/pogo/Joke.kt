package my.rockpilgrim.chucknorriskotlin.data.pogo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Joke(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("joke")
    @Expose
    val text: String
)