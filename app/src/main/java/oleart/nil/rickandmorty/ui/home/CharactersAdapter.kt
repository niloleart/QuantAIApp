package oleart.nil.rickandmorty.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import oleart.nil.rickandmorty.databinding.RowCharacterBinding
import oleart.nil.rickandmorty.databinding.RowLoadingBinding
import oleart.nil.rickandmorty.domain.model.Character
import oleart.nil.rickandmorty.ui.CharactersContract

class CharactersAdapter(
    private val context: Context,
    private val presenter: CharactersContract.Presenter,
    private var characters: MutableList<Character?>,
    var listener: CharactersListener
) : RecyclerView.Adapter<ViewHolder>() {

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    interface CharactersListener {

        fun onClick(character: Character)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        return if (viewType == VIEW_TYPE_ITEM) {
            val binding = RowCharacterBinding.inflate(inflater, parent, false)
            CharactersViewHolder(binding, context, listener)
        } else {
            val binding = RowLoadingBinding.inflate(inflater, parent, false)
            LoadingViewHolder(binding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (characters[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun getItemCount() = characters.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is CharactersViewHolder) {
            holder.bind(characters[position])
        } else {
            (holder as LoadingViewHolder).bind()
        }
    }

    fun filterList(filteredCharacters: MutableList<Character?>) {
        characters = filteredCharacters
        notifyDataSetChanged()
    }
}