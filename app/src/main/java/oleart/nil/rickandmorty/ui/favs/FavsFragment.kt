package oleart.nil.rickandmorty.ui.favs

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.AndroidSupportInjection
import oleart.nil.rickandmorty.R
import oleart.nil.rickandmorty.base.BaseFragment
import oleart.nil.rickandmorty.base.hide
import oleart.nil.rickandmorty.base.launchProcessActivity
import oleart.nil.rickandmorty.base.show
import oleart.nil.rickandmorty.databinding.FragmentFavsBinding
import oleart.nil.rickandmorty.domain.model.Character
import oleart.nil.rickandmorty.presentation.detail.CharacterDetailActivity
import javax.inject.Inject

class FavsFragment() : BaseFragment<FragmentFavsBinding>(FragmentFavsBinding::inflate), FavsContract.View,
    FavCharactersAdapter.FavCharactersListener {

    @Inject
    lateinit var presenter: FavsContract.Presenter

    private lateinit var adapter: FavCharactersAdapter

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
    }

    private fun setToolbar() {
        with(binding.toolbarFavs) {
            inflateMenu(R.menu.toolbar_process)
            title = "Favorites"
        }
    }

    override fun onResume() {
        presenter.getFavCharacters()
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onClick(character: Character) {
        launchProcessActivity(CharacterDetailActivity.makeIntent(context, character))
    }

    override fun showPlaceholder() {
        binding.rvFavsCharacters.hide()
        binding.llPlaceholder.show()
    }

    override fun setRV(favs: List<Character?>) {
        binding.llPlaceholder.hide()
        binding.rvFavsCharacters.show()
        binding.rvFavsCharacters.layoutManager = LinearLayoutManager(context)
        adapter = FavCharactersAdapter(requireContext(), presenter, favs.toMutableList(), this)
        binding.rvFavsCharacters.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}