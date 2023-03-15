package oleart.nil.rickandmorty.ui.home

import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import oleart.nil.rickandmorty.databinding.RowLoadingBinding

class LoadingViewHolder(
    var binding: RowLoadingBinding
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var progressBar: ProgressBar

    fun bind() {
        progressBar = binding.pbRv
    }
}