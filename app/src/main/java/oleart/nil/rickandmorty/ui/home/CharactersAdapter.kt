package oleart.nil.rickandmorty.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import oleart.nil.rickandmorty.domain.model.Characters

class CharactersAdapter(
    private val context: Context,
    private val presenter: HomeContract.Presenter,
    private val character: Characters
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_ITEM) {
            val inflater = LayoutInflater.from(context)
            return CharactersViewHolder(context, inflater, parent)
        } else {
            val inflater = LayoutInflater.from(context)
            return LoadingViewHolder(context, inflater, parent)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (character.characters[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun getItemCount() = character.characters.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is CharactersViewHolder) {
            (holder as CharactersViewHolder).bind(character.characters[position])
        } else {
            (holder as LoadingViewHolder).bind()
        }
    }
}