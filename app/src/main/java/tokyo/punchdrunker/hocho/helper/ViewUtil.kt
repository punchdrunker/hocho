package tokyo.punchdrunker.hocho

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


fun bitmapFromVectorDrawable(context: Context, drawableId: Int): Bitmap {
    val drawable = ContextCompat.getDrawable(context, drawableId)
    val bitmap = Bitmap.createBitmap(drawable!!.intrinsicWidth,
            drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)

    return bitmap
}

/**
 * ツールバー用のWindowInsetsを適用（上部のシステムバーに対応）
 * マージンを使用してツールバーをシステムバーの下に配置
 */
fun View.applyTopSystemBarInsets() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, windowInsets ->
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
        val layoutParams = view.layoutParams as? android.view.ViewGroup.MarginLayoutParams
        layoutParams?.topMargin = insets.top
        view.layoutParams = layoutParams
        windowInsets
    }
}

/**
 * ボトムナビゲーション用のWindowInsetsを適用（下部のシステムバーに対応）
 * マージンを使用してボトムナビゲーションをシステムバーの上に配置
 */
fun View.applyBottomSystemBarInsets() {
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, windowInsets ->
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
        val layoutParams = view.layoutParams as? android.view.ViewGroup.MarginLayoutParams
        layoutParams?.bottomMargin = insets.bottom
        view.layoutParams = layoutParams
        windowInsets
    }
}