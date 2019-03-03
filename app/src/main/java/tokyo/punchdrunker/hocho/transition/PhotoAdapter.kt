package tokyo.punchdrunker.hocho.transition

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import tokyo.punchdrunker.hocho.R

class PhotoAdapter constructor(private val navigator: TransitionNavigator) : RecyclerView.Adapter<PhotoViewHolder>() {
    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_photo, parent, false)
        return PhotoViewHolder(view, navigator)
    }

    override fun getItemCount() = PhotoStore.getCount()
}

class PhotoViewHolder constructor(view: View, private val navigator: TransitionNavigator) : RecyclerView.ViewHolder(view), View.OnClickListener {
    private val photoView: ImageView = view.findViewById(R.id.card_photo)

    init {
        photoView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        navigator.transition(v!!, adapterPosition)
    }

    fun onBind(position: Int) {
        photoView.setImageResource(PhotoStore.getImage(position))
    }
}
