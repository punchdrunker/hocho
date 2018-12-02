package tokyo.punchdrunker.hocho

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class PhotoAdapter : RecyclerView.Adapter<PhotoViewHolder>() {
    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.onBind()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_photo, parent, false)
        return PhotoViewHolder(view)
    }

    override fun getItemCount() = 32
}

class PhotoViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {
    val photoView: ImageView
    init {
        photoView = view.findViewById(R.id.card_photo)
    }
    fun onBind() {
        photoView.setImageResource(R.drawable.img_cat)
    }
}
