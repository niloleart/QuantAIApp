package oleart.nil.rickandmorty.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_character.view.iv_character
import kotlinx.android.synthetic.main.row_character.view.ll_parent
import kotlinx.android.synthetic.main.row_character.view.tv_name
import kotlinx.android.synthetic.main.row_character.view.tv_species
import oleart.nil.rickandmorty.R
import oleart.nil.rickandmorty.domain.model.Character
import oleart.nil.rickandmorty.ui.home.CharactersAdapter.CharactersListener

class CharactersViewHolder(
    var context: Context,
    inflater: LayoutInflater,
    parent: ViewGroup,
    var listener: CharactersListener
) : RecyclerView.ViewHolder(inflater.inflate(R.layout.row_character, parent, false)) {

    private lateinit var character: Character

    fun bind(character: Character?) {
        this.character = character!!
        paintData()
    }

    private fun paintData() {
        with(itemView.ll_parent) {
            Glide.with(context).load(character.image).circleCrop().into(iv_character)
            tv_name.text = character.name
            tv_species.text = character.species

            this.setOnClickListener {
                listener.onClick(character)
            }
        }
    }
}