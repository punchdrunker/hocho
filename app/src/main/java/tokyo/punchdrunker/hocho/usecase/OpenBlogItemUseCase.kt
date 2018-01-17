package tokyo.punchdrunker.hocho.usecase

import android.content.Context
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import tokyo.punchdrunker.hocho.R
import tokyo.punchdrunker.hocho.ViewUtil.bitmapFromVectorDrawable

class OpenBlogItemUseCase constructor(val context: Context) {
    fun run(url: String) {
        context.run {
            val backArrow = bitmapFromVectorDrawable(this, R.drawable.ic_arrow_back)
            val tabsIntent = CustomTabsIntent.Builder()
                    .setShowTitle(true)
                    .setCloseButtonIcon(backArrow)
                    .setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                    .setStartAnimations(this, R.anim.slide_in_right, R.anim.slide_out_left)
                    .setExitAnimations(this, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    .build()

            tabsIntent.launchUrl(this, Uri.parse(url))
        }
    }
}