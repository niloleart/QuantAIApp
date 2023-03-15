package oleart.nil.rickandmorty.ui.home

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.AndroidSupportInjection
import oleart.nil.rickandmorty.base.BaseFragment
import oleart.nil.rickandmorty.base.launchModalActivity
import oleart.nil.rickandmorty.base.registerActivityResult
import oleart.nil.rickandmorty.databinding.FragmentHomeBinding
import oleart.nil.rickandmorty.domain.model.Character
import oleart.nil.rickandmorty.domain.model.Characters
import oleart.nil.rickandmorty.presentation.detail.CharacterDetailActivity
import oleart.nil.rickandmorty.presentation.detail.CharacterDetailActivity.Companion.RESULT_CHAR_ID
import oleart.nil.rickandmorty.presentation.detail.CharacterDetailActivity.Companion.RESULT_FAV
import oleart.nil.rickandmorty.ui.home.HomeContract.Presenter
import javax.inject.Inject

class HomeFragment(private var characters: Characters) :
    BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate), HomeContract.View,
    CharactersAdapter.CharactersListener {

    @Inject
    lateinit var presenter: Presenter

    private val detailCharacterResultLauncher = registerActivityResult(::resultCharacterDetail)

    private lateinit var adapter: CharactersAdapter
    private var isLoading = false
    private var lastLoadedPage = 1

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        lastLoadedPage = presenter.getLastLoadedPage(characters.info.nextPage)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRV()
        initScrollListener()
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
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == characters.characters.size - 1) {
                        loadMore()
                        lastLoadedPage++
                        isLoading = true
                    }
                }
            }
        })
    }

    private fun loadMore() {
        characters.characters.add(null)
        adapter.notifyItemInserted(characters.characters.size - 1)
        presenter.getMoreCharacters(lastLoadedPage)
    }

    override fun addMoreCharacters(characters: Characters) {
        this.characters.info = characters.info

        Handler(Looper.getMainLooper()).postDelayed(
            {
                this.characters.characters.removeAt(this.characters.characters.size - 1)
                val scrollPosition = this.characters.characters.size
                adapter.notifyItemRemoved(scrollPosition)
                var currentSize = scrollPosition
                val nextLimit = currentSize + characters.characters.size
                var position = 0
                while (currentSize - 1 < nextLimit - 1) {
                    this.characters.characters.add(characters.characters[position])
                    position++
                    currentSize++
                }
                adapter.notifyDataSetChanged()
                isLoading = false
                updateDB()
            }, 2000
        )
    }

    private fun updateDB() {
        characters.characters.removeAll(listOf(null))
        presenter.updateDB(characters)
    }

    override fun onPause() {
        presenter.updateDB(characters)
        super.onPause()
    }

    override fun onClick(character: Character) {
        val intent = CharacterDetailActivity.makeIntent(context, character)
        launchModalActivity(intent, detailCharacterResultLauncher)
    }

    private fun resultCharacterDetail(activityResult: ActivityResult?) {
        activityResult?.let {
            val isFaved = activityResult.data?.getBooleanExtra(RESULT_FAV, false)
            isFaved.let {
                val id = activityResult.data?.getIntExtra(RESULT_CHAR_ID, -1)
                val favedChar: Character? = characters.characters.find { it!!.id == id }
                favedChar?.let {
                    it.isFavorite = isFaved!!
                }
                val index: Int = characters.characters.indexOf(favedChar)
                adapter.notifyItemChanged(index)
            }
        }
    }
}