package my.rockpilgrim.chucknorriskotlin.api

class JokeRepository {

    suspend fun getJokes(count: Int) =
        Api.create().getJokes(count)
}