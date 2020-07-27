package my.rockpilgrim.chucknorriskotlin.viewModels

import androidx.lifecycle.ViewModel
import my.rockpilgrim.chucknorriskotlin.api.Api

class BaseViewModel : ViewModel() {

    var api: Api = Api.create()
/*
    fun <T> requestLiveData(
        liveData: MutableLiveData<Lifecycle.Event<T>,
                >
    ) {

    }*/

}