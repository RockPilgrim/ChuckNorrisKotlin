package my.rockpilgrim.chucknorriskotlin.data.pogo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class JokeList(
    @SerializedName("type")
    @Expose
    val type: String,
    @SerializedName(value = "value")
    @Expose
    val jokes: List<Joke>
)