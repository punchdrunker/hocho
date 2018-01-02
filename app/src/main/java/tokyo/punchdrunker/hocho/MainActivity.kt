package tokyo.punchdrunker.hocho

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import tokyo.punchdrunker.hocho.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener({ item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, ArticlesFragment.newInstance()).commitAllowingStateLoss()
                }
                R.id.navigation_bookmarks -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, BookmarksFragment.newInstance()).commitAllowingStateLoss()
                }
                R.id.navigation_help -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, HelpFragment.newInstance()).commitAllowingStateLoss()
                }
                else -> throw NotImplementedError()
            }
            supportActionBar?.title = ""
            binding.toolbarTitle.text = item.title

            true
        })
        binding.bottomNavigation.selectedItemId = R.id.navigation_home
    }
}
