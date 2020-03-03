package tokyo.punchdrunker.hocho

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import android.widget.TextView

class CustomButton(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    var listener: OnClickListener? = null

    constructor(context: Context) : this(context, null)

    init {
        LayoutInflater.from(getContext()).inflate(R.layout.button_progress, this)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        listener = l
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_UP) {
            if (listener != null) {
                post { listener?.onClick(this@CustomButton) }
            }
        }

        return super.dispatchTouchEvent(ev)
    }

    fun toggle() {
        val textView = findViewById<TextView>(R.id.textView)
        textView.visibility = if (textView.visibility == View.GONE) View.VISIBLE else View.GONE
        
        val bar = findViewById<ProgressBar>(R.id.progressBar)
        bar.visibility = if (bar.visibility == View.GONE) View.VISIBLE else View.GONE
    }
}