package oleart.nil.rickandmorty.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_loading.view.pb_rv
import oleart.nil.rickandmorty.R

class LoadingViewHolder(
    var context: Context,
    inflater: LayoutInflater,
    parent: ViewGroup
) : RecyclerView.ViewHolder(inflater.inflate(R.layout.row_loading, parent, false)) {

    private lateinit var progressBar: ProgressBar

    fun bind() {
        progressBar = itemView.pb_rv
    }

    private fun showLoadingView() {
    }
}