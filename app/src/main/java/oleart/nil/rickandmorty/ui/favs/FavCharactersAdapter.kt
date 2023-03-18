package oleart.nil.rickandmorty.ui.favs

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import oleart.nil.rickandmorty.databinding.RowCharacterFavBinding
import oleart.nil.rickandmorty.domain.model.Character
import oleart.nil.rickandmorty.ui.CharactersContract

class FavCharactersAdapter(
    private val context: Context,
    private val presenter: CharactersContract.Presenter,
    private val characters: MutableList<Character?>,
    var listener: FavCharactersListener
) : RecyclerView.Adapter<FavCharactersViewHolder>() {

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    interface FavCharactersListener {

        fun onClick(character: Character)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavCharactersViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = RowCharacterFavBinding.inflate(inflater, parent, false)
        return FavCharactersViewHolder(binding, context, listener)
    }

    override fun getItemViewType(position: Int): Int {
        return if (characters[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun getItemCount() = characters.size
    override fun onBindViewHolder(holder: FavCharactersViewHolder, position: Int) {
        holder.bind(characters[position])
    }
}