package my.rockpilgrim.chucknorriskotlin.api

import my.rockpilgrim.chucknorriskotlin.data.pogo.JokeList
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {


    @GET("/jokes/random/{count}/?escape=javascript")
    suspend fun getJokes(@Path(value = "count")count: Int): JokeList

    companion object Network{
        private const val BASE_URL = "https://api.icndb.com"

        fun create(): Api =
            retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)
    }
}