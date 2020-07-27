package my.rockpilgrim.chucknorriskotlin.adapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("app:adapterList")
fun setAdapter(recycler: RecyclerView, adapter: JokeAdapter) {
    recycler.adapter = adapter
}