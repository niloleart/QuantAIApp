package oleart.nil.rickandmorty.ui.home

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import oleart.nil.rickandmorty.R
import oleart.nil.rickandmorty.databinding.RowCharacterBinding
import oleart.nil.rickandmorty.domain.model.Character
import oleart.nil.rickandmorty.ui.home.CharactersAdapter.CharactersListener

class CharactersViewHolder(
    val binding: RowCharacterBinding,
    var context: Context,
    var listener: CharactersListener
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