package tokyo.punchdrunker.hocho

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import timber.log.Timber
import tokyo.punchdrunker.hocho.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Dynamic Feature Moduleのロードの準備
    private lateinit var manager: SplitInstallManager
    private val listener = SplitInstallStateUpdatedListener { state ->
        state.moduleNames().forEach { name ->
            when (state.status()) {
                SplitInstallSessionStatus.DOWNLOADING -> {
                    //  In order to see this, the application has to be uploaded to the Play Store.
                    displayLoadingState("Downloading $name")
                }
                SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                    /*
                      This may occur when attempting to download a sufficiently large module.

                      In order to see this, the application has to be uploaded to the Play Store.
                      Then features can be requested until the confirmation path is triggered.
                     */
                    startIntentSender(state.resolutionIntent().intentSender, null, 0, 0, 0)
                }
                SplitInstallSessionStatus.INSTALLED -> {
                    startGallery()
                }

                SplitInstallSessionStatus.INSTALLING -> displayLoadingState("Installing $name")
                SplitInstallSessionStatus.FAILED -> {
                    Timber.e("Error: ${state.errorCode()} for module ${state.moduleNames()}")
                }
            }
        }
    }

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

        manager = SplitInstallManagerFactory.create(this)
        setSupportActionBar(binding.toolbar)
        setupBottomNavigation()
        setupDrawer()
    }

    override fun onResume() {
        // Listener can be registered even without directly triggering a download.
        manager.registerListener(listener)
        super.onResume()
    }

    override fun onPause() {
        // Make sure to dispose of the listener once it's no longer needed.
        manager.unregisterListener(listener)
        super.onPause()
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
                R.id.nav_item_dynamic_feature -> loadAndLaunchDynamicGallery()
            }
            true
        }
    }

    private fun displayLoadingState(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    // Dynamic featureなActivityをインストールしてから起動する
    private fun loadAndLaunchDynamicGallery() {
        displayLoadingState("Loading dynamic featrure module")
        // インストール済みならそのまま起動する
        if (manager.installedModules.contains(GALLERY_MODULE_NAME)) {
            displayLoadingState("Already installed")
            startGallery()
            return
        }

        // Dynamic featureをインストール
        val request = SplitInstallRequest.newBuilder()
                .addModule(GALLERY_MODULE_NAME)
                .build()
        manager.startInstall(request)

        displayLoadingState("Installing dynamic featrure module")
    }
    
    private fun startGallery() {
        // moduleをインストールせずに呼ぶとクラッシュする
        Intent().setClassName(packageName, GALLERY_CLASSNAME)
                .also {
                    startActivity(it)
                }
    }
}

private const val ONDEMAND_PACKAGE_NAME = "tokyo.punchdrunker.dynamic.gallery"
private const val GALLERY_CLASSNAME = "$ONDEMAND_PACKAGE_NAME.MainActivity"
private const val GALLERY_MODULE_NAME = "gallery"