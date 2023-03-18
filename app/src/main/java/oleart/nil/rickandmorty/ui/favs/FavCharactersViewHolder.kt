package oleart.nil.rickandmorty.ui.favs

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import oleart.nil.rickandmorty.R
import oleart.nil.rickandmorty.databinding.RowCharacterFavBinding
import oleart.nil.rickandmorty.domain.model.Character
import oleart.nil.rickandmorty.ui.favs.FavCharactersAdapter.FavCharactersListener

class FavCharactersViewHolder(
    val binding: RowCharacterFavBinding,
    var context: Context,
    var listener: FavCharactersListener
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var character: Character

    fun bind(character: Character?) {
        this.character = character!!
        paintData()
    }

    private fun paintData() {
        with(binding) {
            Glide.with(context)
                .load(character.image)
                .placeholder(R.drawable.rickandmorty_placeholder)
                .circleCrop()
                .into(this.ivCharacter)
            this.tvName.text = character.name
            this.tvSpecies.text = character.species
        }
        binding.llParent.setOnClickListener {
            listener.onClick(character)
        }
    }
}