package tokyo.punchdrunker.hocho.transition

import android.content.Context
import tokyo.punchdrunker.hocho.R

class PhotoStore {
    companion object {
        private val images = arrayOf(R.drawable.img_cat, R.drawable.img_dog, R.drawable.img_parts, R.drawable.img_view, R.drawable.img_sea)
        private const val storeName = "storeName"
        private const val positionKey = "positionKey"

        fun getCount() = 35

        fun getImage(position: Int): Int = images[position % 5]

        fun getCurrentPosition(context: Context): Int {
            return context.getSharedPreferences(storeName, Context.MODE_PRIVATE).getInt(positionKey, 0)
        }

        fun setCurrentPosition(context: Context, position: Int) {
            context.getSharedPreferences(storeName, Context.MODE_PRIVATE).edit().also {
                it.putInt(positionKey, position)
                it.apply()
            }
        }
    }
}
