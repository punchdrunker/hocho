package tokyo.punchdrunker.hocho

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_UNSPECIFIED
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.databinding.DataBindingUtil
import tokyo.punchdrunker.hocho.databinding.ActivityMainBinding
import tokyo.punchdrunker.hocho.transition.FromActivity

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.switch_mode -> {
                switchMode()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun switchMode() {
        val defaultMode = AppCompatDelegate.getDefaultNightMode()
        val mode = if (defaultMode == MODE_NIGHT_YES
                || defaultMode == MODE_NIGHT_FOLLOW_SYSTEM
                || defaultMode == MODE_NIGHT_UNSPECIFIED)
            MODE_NIGHT_NO else MODE_NIGHT_YES
        AppCompatDelegate.setDefaultNightMode(mode)
        restartActivity()
    }

    private fun restartActivity() {
        val intent = intent
        overridePendingTransition(0, 0)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        finish()

        overridePendingTransition(0, 0)
        startActivity(intent)
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
                    supportFragmentManager.beginTransaction().replace(R.id.container, WikiFragment.newInstance()).commitAllowingStateLoss()
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
                R.id.nav_item_notification -> startActivity(Intent(this, NotificationActivity::class.java))
                R.id.nav_item_async -> startActivity(Intent(this, AsyncActivity::class.java))
                R.id.nav_item_lifecycle -> startActivity(Intent(this, LifecycleActivity::class.java))
                R.id.nav_item_dynamic_feature -> startActivity(Intent(this, DynamicFeatureActivity::class.java))
                R.id.nav_item_progress -> startActivity(Intent(this, ProgressActivity::class.java))
                R.id.nav_item_transition -> startActivity(Intent(this, FromActivity::class.java))
            }
            true
        }
    }
}