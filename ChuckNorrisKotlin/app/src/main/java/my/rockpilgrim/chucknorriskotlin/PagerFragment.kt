package my.rockpilgrim.chucknorriskotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.jokes_pager_fragment.*
import kotlinx.android.synthetic.main.jokes_pager_fragment.view.*
import my.rockpilgrim.chucknorriskotlin.adapters.JokePagerAdapter
import my.rockpilgrim.chucknorriskotlin.adapters.JokePagerAdapter.Companion.JOKES_FRAGMENT
import my.rockpilgrim.chucknorriskotlin.adapters.JokePagerAdapter.Companion.WEB_FRAGMENT
import my.rockpilgrim.chucknorriskotlin.databinding.JokesPagerFragmentBinding

class PagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: JokesPagerFragmentBinding =
            JokesPagerFragmentBinding.inflate(inflater, container, false)

//        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        initPager(binding.root)
        return binding.root
    }

    private fun initPager(root:View) {
//        val pagerAdapter = JokePagerAdapter(this)
        val viewPager = root.fragmentsViewPager
        val bottomNavigation = root.bottomNavigation
//        viewPager.adapter = pagerAdapter


        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bottomNavigation.selectedItemId =
                    if (position == JOKES_FRAGMENT)
                        R.id.jokeTab
                    else
                        R.id.webTab
            }
        })


        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.jokeTab -> {
                    fragmentsViewPager.currentItem = JOKES_FRAGMENT
                    viewPager.isUserInputEnabled = true
                    true
                }
                R.id.webTab -> {
                    fragmentsViewPager.currentItem = WEB_FRAGMENT
                    viewPager.isUserInputEnabled = false
                    true
                }
                else -> false
            }
        }
    }
}