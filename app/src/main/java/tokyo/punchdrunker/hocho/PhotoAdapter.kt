package tokyo.punchdrunker.hocho

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class PhotoAdapter constructor(private val navigator: TransitionNavigator) : RecyclerView.Adapter<PhotoViewHolder>() {
    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_photo, parent, false)
        return PhotoViewHolder(view, navigator)
    }

    override fun getItemCount() = 32
}

class PhotoViewHolder constructor(view: View, private val navigator: TransitionNavigator) : RecyclerView.ViewHolder(view), View.OnClickListener {
    private val photoView: ImageView = view.findViewById(R.id.card_photo)
    private val images = arrayOf(R.drawable.img_cat, R.drawable.img_dog, R.drawable.img_parts, R.drawable.img_view)

    init {
        photoView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        navigator.transition(v!!, adapterPosition)
    }

    fun onBind(position: Int) {
        val key = position % 4
        photoView.setImageResource(images[key])
    }
}
