package oleart.nil.rickandmorty.base

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.Fragment
import oleart.nil.rickandmorty.R
import oleart.nil.rickandmorty.base.TransitionType.MODAL
import oleart.nil.rickandmorty.base.TransitionType.SLIDE

fun Fragment.launchProcessActivity(intent: Intent, responseLauncher: ActivityResultLauncher<Intent>? = null) {
    intent.putExtra(EXTRA_TRANSITION_TYPE, SLIDE)
    if (responseLauncher == null) {
        startActivity(intent)
    } else {
        responseLauncher.launch(intent)
    }
    this.activity?.overridePendingTransition(R.anim.slide_in_right_activity, R.anim.slide_out_left_activity)
}

fun Fragment.registerActivityResult(
    getResult: ((output: ActivityResult?) -> Unit)
): ActivityResultLauncher<Intent> {
    return registerForActivityResult(StartActivityForResult()) {
        getResult.invoke(it)
    }
}

fun Fragment.launchModalActivity(intent: Intent, responseLauncher: ActivityResultLauncher<Intent>? = null) {
    intent.putExtra(EXTRA_TRANSITION_TYPE, MODAL)
    if (responseLauncher == null) {
        startActivity(intent)
    } else {
        responseLauncher.launch(intent)
    }
    this.activity?.overridePendingTransition(R.anim.slide_in_bottom_activity, R.anim.slide_out_top_activity)
}