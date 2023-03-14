package oleart.nil.rickandmorty.base

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import oleart.nil.rickandmorty.R
import oleart.nil.rickandmorty.base.TransitionType.MODAL
import oleart.nil.rickandmorty.base.TransitionType.SLIDE

const val EXTRA_TRANSITION_TYPE = "extra_transitions_type"

enum class TransitionType {
    MODAL, SLIDE, DEFAULT
}

fun AppCompatActivity.launchProcessActivity(
    intent: Intent,
    responseLauncher: ActivityResultLauncher<Intent>? = null
) {
    intent.putExtra(EXTRA_TRANSITION_TYPE, SLIDE)
    if (responseLauncher == null) {
        startActivity(intent)
    } else {
        responseLauncher.launch(intent)
    }
    overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_left_activity)
}

fun AppCompatActivity.launchModalActivity(intent: Intent, responseLauncher: ActivityResultLauncher<Intent>? = null) {
    intent.putExtra(EXTRA_TRANSITION_TYPE, MODAL)
    if (responseLauncher == null) {
        startActivity(intent)
    } else {
        responseLauncher.launch(intent)
    }
    overridePendingTransition(R.anim.slide_in_bottom_activity, R.anim.slide_out_top_activity)
}