package my.rockpilgrim.chucknorriskotlin.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import my.rockpilgrim.chucknorriskotlin.JokeFragment
import my.rockpilgrim.chucknorriskotlin.WebFragment

class MainPagerAdapter(fm: FragmentManager, behavior: Int) : FragmentPagerAdapter(fm, behavior) {

    val JOKES_FRAGMENT = 0
    val WEB_FRAGMENT = 1

    override fun getItem(position: Int): Fragment {
        if (position == JOKES_FRAGMENT) {
            return JokeFragment()
        }else{
            return WebFragment()
        }
    }

    override fun getCount(): Int = 2
}