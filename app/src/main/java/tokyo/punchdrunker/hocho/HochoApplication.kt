package tokyo.punchdrunker.hocho

import android.app.Application
import com.facebook.stetho.Stetho
import timber.log.Timber

class HochoApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        // デバッグビルド限定でデバッグ用ツールの設定
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(this)
        }
    }
}