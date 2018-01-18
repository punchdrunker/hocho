package tokyo.punchdrunker.hocho

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import tokyo.punchdrunker.hocho.databinding.ActivityMainBinding




class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    private val actionBarDrawerToggle by lazy {
        ActionBarDrawerToggle(
                this,
                binding.drawerLayout,
                binding.toolbar,
                R.string.content_description_drawer_open, /* "open drawer" description for accessibility */
                R.string.content_description_drawer_close /* "close drawer" description for accessibility */
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)
        setupBottomNavigation()
        setupDrawer()
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

    private fun setupDrawer() {
        actionBarDrawerToggle.isDrawerIndicatorEnabled = true
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        binding.navigation.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_item_layout -> startActivity(Intent(this, LayoutActivity::class.java))
                R.id.nav_item_setting -> startActivity(Intent(this, SettingsActivity::class.java))
            }
            true
        }
    }
}
