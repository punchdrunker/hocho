package tokyo.punchdrunker.hocho

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import timber.log.Timber
import tokyo.punchdrunker.hocho.databinding.ActivityDynamicFeatureBinding

class DynamicFeatureActivity : AppCompatActivity() {
    private lateinit var manager: SplitInstallManager

    private val binding: ActivityDynamicFeatureBinding by lazy {
        DataBindingUtil.setContentView<ActivityDynamicFeatureBinding>(this, R.layout.activity_dynamic_feature)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.activity = this
        manager = SplitInstallManagerFactory.create(this)
        setupToolbar()
    }

    override fun onResume() {
        super.onResume()

        // Listener can be registered even without directly triggering a download.
        manager.registerListener(listener)
    }

    override fun onStart() {
        super.onStart()

        if (manager.installedModules.contains(GALLERY_MODULE_NAME)) {
            binding.notInstalled.visibility = View.GONE
            binding.installed.visibility = View.VISIBLE
        } else {
            binding.notInstalled.visibility = View.VISIBLE
            binding.installed.visibility = View.GONE
        }
    }

    override fun onPause() {
        // Make sure to dispose of the listener once it's no longer needed.
        manager.unregisterListener(listener)
        super.onPause()
    }

    // Open Button
    fun openGallery(view: View) {
        startGallery()
    }

    // Install Button
    // Dynamic featureなActivityをインストールしてから起動する
    fun loadAndLaunchDynamicGallery(view: View) {
        // Dynamic featureをインストール
        val request = SplitInstallRequest.newBuilder()
                .addModule(GALLERY_MODULE_NAME)
                .build()
        manager.startInstall(request)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = ""
            setDisplayHomeAsUpEnabled(true)
        }
        binding.toolbarTitle.text = getString(R.string.title_activity_dynamic_feature)
    }

    private fun displayLoadingState(message: String) {
        binding.status.text = message
    }

    // Dynamic Feature Moduleのロードの準備
    private val listener = SplitInstallStateUpdatedListener { state ->
        state.moduleNames().forEach { name ->
            when (state.status()) {
                SplitInstallSessionStatus.DOWNLOADING -> {
                    // 表示を確認するには、Play Storeにアップロードしたもので確認する必要がある
                    displayLoadingState("Downloading $name")
                }
                SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                    // モジュールサイズが大きい時に発火するイベント
                    // 表示を確認するには、Play Storeにアップロードしたもので確認する必要がある
                    startIntentSender(state.resolutionIntent().intentSender, null, 0, 0, 0)
                }
                SplitInstallSessionStatus.INSTALLED -> {
                    startGallery()
                }

                SplitInstallSessionStatus.INSTALLING -> displayLoadingState("Installing $name")
                SplitInstallSessionStatus.FAILED -> {
                    displayLoadingState("Error: ${state.errorCode()} for module ${state.moduleNames()}")
                    Timber.e("Error: ${state.errorCode()} for module ${state.moduleNames()}")
                }
            }
        }
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
private const val GALLERY_CLASSNAME = "$ONDEMAND_PACKAGE_NAME.GalleryActivity"
private const val GALLERY_MODULE_NAME = "gallery"
