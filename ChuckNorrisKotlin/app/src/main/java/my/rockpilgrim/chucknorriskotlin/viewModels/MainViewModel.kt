package my.rockpilgrim.chucknorriskotlin.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import my.rockpilgrim.chucknorriskotlin.R
import my.rockpilgrim.chucknorriskotlin.api.JokeRepository
import my.rockpilgrim.chucknorriskotlin.data.Event
import my.rockpilgrim.chucknorriskotlin.data.Event.Error
import my.rockpilgrim.chucknorriskotlin.data.Event.Success
import my.rockpilgrim.chucknorriskotlin.data.pogo.Joke
import retrofit2.Retrofit
import java.lang.Exception

class MainViewModel : ViewModel() {

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
        private const val MAX_COUNT = 500
    }

    val jokeList: MutableLiveData<Event> = MutableLiveData()

    private var count: Int = -1

    init {
        Log.i(TAG, "init()")
    }

    fun loadJokes(context: Context, count: Int) {
        jokeList.postValue(Event.Loading)
        if (!checkCount(count)) {
            jokeList.postValue(Error(context.getString(R.string.count_error)))
            return
        }
        this.viewModelScope.launch(Dispatchers.IO) {
            Log.i(TAG, "initJokes() corutines ${Thread.currentThread().name}")
            try {
                val list: List<Joke> = JokeRepository().getJokes(count).jokes
                jokeList.postValue(Success(list))
            } catch (e: Exception){
                Log.e(TAG, "loadJokes() error ${e.message}", e)
                jokeList.postValue(Error(context.getString(R.string.error)))
            }
        }
    }
    fun reloadJokes(context:Context) {
        loadJokes(context, count)
    }

    private fun checkCount(count: Int) :Boolean{
        if (count in 1 until MAX_COUNT) {
            this.count = count
            return true
        }
        return false
    }
}