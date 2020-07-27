package my.rockpilgrim.chucknorriskotlin

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.jokes_pager_fragment.*
import kotlinx.android.synthetic.main.jokes_pager_fragment.view.*
import my.rockpilgrim.chucknorriskotlin.adapters.JokePagerAdapter
import my.rockpilgrim.chucknorriskotlin.databinding.JokesPagerFragmentBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=DataBindingUtil.setContentView<JokesPagerFragmentBinding>(this, R.layout.jokes_pager_fragment)

        initPager(binding.root)
    }

    private fun initPager(root: View) {
        val pagerAdapter = JokePagerAdapter(this)
        val viewPager = root.fragmentsViewPager
        val bottomNavigation = root.bottomNavigation
        viewPager.adapter = pagerAdapter

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bottomNavigation.selectedItemId =
                    if (position == JokePagerAdapter.JOKES_FRAGMENT)
                        R.id.jokeTab
                    else
                        R.id.webTab
            }
        })


        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.jokeTab -> {
                    fragmentsViewPager.currentItem = JokePagerAdapter.JOKES_FRAGMENT
                    viewPager.isUserInputEnabled = true
                    true
                }
                R.id.webTab -> {
                    fragmentsViewPager.currentItem = JokePagerAdapter.WEB_FRAGMENT
                    viewPager.isUserInputEnabled = false
                    true
                }
                else -> false
            }
        }
    }
}