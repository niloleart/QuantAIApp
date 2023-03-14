package oleart.nil.rickandmorty.ui.home

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.AndroidSupportInjection
import oleart.nil.rickandmorty.base.BaseFragment
import oleart.nil.rickandmorty.databinding.FragmentHomeBinding
import oleart.nil.rickandmorty.domain.model.Characters
import oleart.nil.rickandmorty.ui.home.HomeContract.Presenter
import javax.inject.Inject

class HomeFragment(private var characters: Characters) :
    BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate), HomeContract.View {

    @Inject
    lateinit var presenter: Presenter

    private lateinit var adapter: CharactersAdapter
    private var isLoading = false
    private var lastLoadedPage = 1

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setRV()
        initScrollListener()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setRV() {
        binding.rvCharacters.layoutManager = LinearLayoutManager(context)
        adapter = CharactersAdapter(requireContext(), presenter, characters)
        binding.rvCharacters.adapter = adapter
    }

    private fun initScrollListener() {
        binding.rvCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

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
//                    this.characters.characters.addAll(characters.characters)
                    position++
                    currentSize++
                }
                adapter.notifyDataSetChanged()
                isLoading = false

            }, 2000
        )
    }
}