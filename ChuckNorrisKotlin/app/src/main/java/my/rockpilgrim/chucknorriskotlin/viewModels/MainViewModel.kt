package my.rockpilgrim.chucknorriskotlin.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import my.rockpilgrim.chucknorriskotlin.api.JokeRepository
import my.rockpilgrim.chucknorriskotlin.data.Event
import my.rockpilgrim.chucknorriskotlin.data.pogo.Joke

class MainViewModel : ViewModel() {

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
        private const val MAX_COUNT = 500

    }
    val jokeList: MutableLiveData<Event> = MutableLiveData()

    private var count: Int = -1
    val currentName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    init {
        Log.i(TAG, "init()")
    }

    fun loadJokes(count: Int) {
        jokeList.postValue(Event.Loading)
        if (!checkCount(count)) {
            jokeList.postValue(Event.Error("Error"))
            return
        }
        this.viewModelScope.launch(Dispatchers.IO) {
            Log.i(TAG, "initJokes() corutines ${Thread.currentThread().name}")
            val list: List<Joke> = JokeRepository().getJokes(count).jokes
            jokeList.postValue(Event.Success(list))
        }
    }
    fun loadJokes() {
        loadJokes(count)
    }

    private fun checkCount(count: Int) :Boolean{
        if (count in 1 until MAX_COUNT) {
            this.count = count
            return true
        }
        return false
    }
}