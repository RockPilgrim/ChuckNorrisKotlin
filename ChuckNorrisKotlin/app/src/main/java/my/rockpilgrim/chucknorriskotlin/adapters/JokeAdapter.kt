package my.rockpilgrim.chucknorriskotlin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_joke.view.*
import my.rockpilgrim.chucknorriskotlin.R
import my.rockpilgrim.chucknorriskotlin.data.pogo.Joke
import my.rockpilgrim.chucknorriskotlin.utils.JokeDiffCallback

class JokeAdapter : ListAdapter<Joke, JokeAdapter.ViewHolder>(JokeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.item_joke, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(joke: Joke) {
            itemView.jokeTextView.text = joke.text
        }
    }
}