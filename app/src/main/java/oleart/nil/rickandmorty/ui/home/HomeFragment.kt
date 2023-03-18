package oleart.nil.rickandmorty.ui.home

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.AndroidSupportInjection
import io.ktor.util.toLowerCasePreservingASCIIRules
import oleart.nil.rickandmorty.R
import oleart.nil.rickandmorty.base.BaseFragment
import oleart.nil.rickandmorty.base.launchModalActivity
import oleart.nil.rickandmorty.databinding.FragmentHomeBinding
import oleart.nil.rickandmorty.domain.model.Character
import oleart.nil.rickandmorty.presentation.detail.CharacterDetailActivity
import oleart.nil.rickandmorty.ui.home.HomeContract.Presenter
import javax.inject.Inject

class HomeFragment(private var characters: MutableList<Character?>) :
    BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate), HomeContract.View,
    CharactersAdapter.CharactersListener {

    @Inject
    lateinit var presenter: Presenter

    private lateinit var adapter: CharactersAdapter
    private var isLoading = false
    private var lastLoadedPage = 1

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        presenter.setInitCharacters(characters)
        lastLoadedPage = 1
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
        setRV()
        initScrollListener()
    }

    private fun setToolbar() {
        with(binding.homeToolbar) {
            inflateMenu(R.menu.toolbar_home)
            title = "Characters"
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.actionbar_menu_search -> {
                        val searchView: SearchView = it.actionView as SearchView
                        searchView.queryHint = "Search"
                        setCloseButton(searchView)
                        setSearchListeners(searchView)
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }
    }

    private fun setSearchListeners(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                query?.let { q -> filter(q) }
                return true
            }
        })
    }

    private fun setCloseButton(searchView: SearchView) {
        with(searchView) {
            val searchCloseButtonId = findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn).id
            val closeButton = findViewById<ImageView>(searchCloseButtonId)
            closeButton.setOnClickListener {
                setQuery("", false)
                clearFocus()
                binding.homeToolbar.collapseActionView()
                onActionViewCollapsed()
            }
        }
    }

    private fun filter(query: String) {
        val filteredCharacters = mutableListOf<Character?>()
        for (character in characters) {
            if (character?.name!!.toLowerCasePreservingASCIIRules()
                    .contains(query.toLowerCasePreservingASCIIRules())
            ) {
                filteredCharacters.add(character)
            }
        }
        adapter.filterList(filteredCharacters)
    }

    private fun setRV() {
        binding.rvCharacters.layoutManager = LinearLayoutManager(context)
        adapter = CharactersAdapter(requireContext(), presenter, characters, this)
        binding.rvCharacters.adapter = adapter
    }

    private fun initScrollListener() {
        binding.rvCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val linearLayoutManager = binding.rvCharacters.layoutManager as LinearLayoutManager

                if (!isLoading) {
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == characters.size - 1) {
                        loadMore()
                        lastLoadedPage++
                        isLoading = true
                    }
                }
            }
        })
    }

    private fun loadMore() {
        characters.add(null)
        adapter.notifyItemInserted(characters.size - 1)
        presenter.getMoreCharacters(lastLoadedPage)
    }

    override fun addMoreCharacters(characters: MutableList<Character?>) {
        Handler(Looper.getMainLooper()).postDelayed(
            {
                this.characters.removeAt(this.characters.size - 1)
                val scrollPosition = this.characters.size
                adapter.notifyItemRemoved(scrollPosition)
                var currentSize = scrollPosition
                val nextLimit = currentSize + characters.size
                var position = 0
                while (currentSize - 1 < nextLimit - 1) {
                    this.characters.add(characters[position])
                    position++
                    currentSize++
                }
                adapter.notifyDataSetChanged()
                isLoading = false
                updateDB()
            }, 500
        )
    }

    private fun updateDB() {
        characters.removeAll(listOf(null))
        if (!characters.contains(null)) {
            presenter.updateDB(characters)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.getAllCharacters(characters)
    }

    override fun onClick(character: Character) {
        presenter.onCharacterClick(character)
    }

    override fun setCharacter(character: Character) {
        goToCharacterDetailActivity(character)
    }

    override fun setCharacterFromDB(character: Character) {
        goToCharacterDetailActivity(character)
    }

    private fun goToCharacterDetailActivity(character: Character) {
        val intent = CharacterDetailActivity.makeIntent(context, character)
        launchModalActivity(intent)
    }

    override fun showError() {
        Toast.makeText(context, "There has been an error", Toast.LENGTH_SHORT).show()
    }

    override fun updateRV(characters: MutableList<Character?>, indexs: List<Int>) {
        for (index in indexs) {
            adapter.notifyItemChanged(index)
        }
    }

    override fun hideLoading() {
        characters.removeLast()
        adapter.notifyItemRemoved(characters.size + 1)
    }
}