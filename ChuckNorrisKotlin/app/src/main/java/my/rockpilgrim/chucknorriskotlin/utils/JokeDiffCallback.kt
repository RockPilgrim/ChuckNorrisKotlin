package my.rockpilgrim.chucknorriskotlin.utils

import androidx.recyclerview.widget.DiffUtil
import my.rockpilgrim.chucknorriskotlin.data.pogo.Joke

class JokeDiffCallback : DiffUtil.ItemCallback<Joke>() {
    override fun areItemsTheSame(oldItem: Joke, newItem: Joke): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Joke, newItem: Joke): Boolean {
        return oldItem.text == newItem.text
    }
}