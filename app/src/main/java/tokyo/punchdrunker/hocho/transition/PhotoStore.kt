package tokyo.punchdrunker.hocho.transition

import tokyo.punchdrunker.hocho.R

class PhotoStore {
    companion object {
        fun getCount() = 32
        fun getImage(position: Int): Int = images[position % 4]

        private val images = arrayOf(R.drawable.img_cat, R.drawable.img_dog, R.drawable.img_parts, R.drawable.img_view)
    }
}