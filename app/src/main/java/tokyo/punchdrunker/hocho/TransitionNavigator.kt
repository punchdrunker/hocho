package tokyo.punchdrunker.hocho

import android.view.View

interface TransitionNavigator {
    fun transition(v: View, position: Int)
}