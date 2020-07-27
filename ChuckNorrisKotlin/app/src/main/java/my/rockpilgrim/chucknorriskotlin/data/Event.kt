package my.rockpilgrim.chucknorriskotlin.data

import my.rockpilgrim.chucknorriskotlin.data.pogo.Joke

sealed class Event {

    class Success(val jokes: List<Joke>) : Event()
    class Error(val message: String) : Event()
    object Loading : Event()

}