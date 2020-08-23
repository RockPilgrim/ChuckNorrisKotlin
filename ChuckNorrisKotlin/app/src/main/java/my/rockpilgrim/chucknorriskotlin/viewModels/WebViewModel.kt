package my.rockpilgrim.chucknorriskotlin.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WebViewModel : ViewModel() {

    companion object{
        private const val BASE_URL = "http://www.icndb.com/api"
    }
/*    sealed class Load{
        object Loading
        class Error(message: String)
        class Success(url: String)
    }*/

    private val mUrl: MutableLiveData<String> = MutableLiveData(BASE_URL)
    val url: LiveData<String> = mUrl

    fun setUrl(url: String) {
        this.mUrl.postValue(url)
    }

}