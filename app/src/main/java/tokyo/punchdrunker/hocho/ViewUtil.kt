package tokyo.punchdrunker.hocho.ViewUtil

import android.content.Context

fun convertDpToPx(dp: Float, context: Context): Float {
    val metrics = context.getResources().getDisplayMetrics()
    return dp * metrics.density
}
