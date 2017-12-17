package tokyo.punchdrunker.hocho

import android.app.Application
import timber.log.Timber

class HochoApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}