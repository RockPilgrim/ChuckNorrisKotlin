package my.rockpilgrim.chucknorriskotlin.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import my.rockpilgrim.chucknorriskotlin.JokeFragment
import my.rockpilgrim.chucknorriskotlin.WebFragment

class JokePagerAdapter(fragment: FragmentActivity) :
    FragmentStateAdapter(fragment) {

    companion object{
        const val JOKES_FRAGMENT:Int = 0
        const val WEB_FRAGMENT:Int = 1
    }

    private val fragmentsCreator:Map<Int,() ->Fragment> = mapOf(
        JOKES_FRAGMENT to {JokeFragment()},
        WEB_FRAGMENT to { WebFragment()}
    )

    override fun getItemCount(): Int = fragmentsCreator.size

    override fun createFragment(position: Int): Fragment {
        return fragmentsCreator[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}