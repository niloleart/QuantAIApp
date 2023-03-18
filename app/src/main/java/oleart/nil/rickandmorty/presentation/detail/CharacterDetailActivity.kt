package oleart.nil.rickandmorty.presentation.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import dagger.android.AndroidInjection
import oleart.nil.rickandmorty.R
import oleart.nil.rickandmorty.base.BaseActivity
import oleart.nil.rickandmorty.base.hide
import oleart.nil.rickandmorty.base.show
import oleart.nil.rickandmorty.databinding.ActivityCharacterDetailBinding
import oleart.nil.rickandmorty.domain.model.Character
import oleart.nil.rickandmorty.presentation.detail.CharacterDetailContract.Presenter
import javax.inject.Inject
import kotlin.properties.Delegates

class CharacterDetailActivity :
    BaseActivity<ActivityCharacterDetailBinding>(), CharacterDetailContract.View {

    private lateinit var character: Character
    private var isFavorite by Delegates.notNull<Boolean>()
    private var description: String = ""

    @Inject
    lateinit var presenter: Presenter

    companion object {

        private const val EXTRA_CHARACTER = "EXTRA_CHARACTER"

        fun makeIntent(context: Context?, character: Character): Intent {
            return Intent(context, CharacterDetailActivity::class.java)
                .putExtra(EXTRA_CHARACTER, character)
        }
    }

    override fun setViewBinding() = ActivityCharacterDetailBinding.inflate(layoutInflater)

    override fun setViewGroup() = binding.root

    override fun getTitleActivity() = ""

    override fun getTypeActionBar() = ActionBarType.DETAIL

    override fun bindToolbar() = binding.toolbar

    override fun finish() {
        if (isFavorite != character.isFavorite || (character.description != null && description != character.description)) {
            presenter.updateCharacter(character)
        }
        super.finish()
        overridePendingTransition(R.anim.slide_in_bottom_activity, R.anim.slide_out_bottom_activity)
    }

    private fun getExtras() {
        this.character = intent.getSerializableExtra(EXTRA_CHARACTER) as Character
        isFavorite = character.isFavorite
        character.description?.let { description = it }
    }

    private fun setUpToolbar() {
        myToolbar?.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
        setSupportActionBar(myToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        with(binding.collapsingToolbar) {
            title = character.name
            setCollapsedTitleTextColor(getColor(R.color.white))
            setExpandedTitleColor(getColor(R.color.white))
        }
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.actionbar_menu_fav -> {
                if (character.isFavorite) {
                    item.setIcon(R.drawable.ic_fav_off)
                } else {
                    item.setIcon(R.drawable.ic_fav_on)
                }
                character.isFavorite = !character.isFavorite
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        getExtras()
        presenter.getDescription(character)
        setUI()
    }

    private fun setUI() {
        setUpToolbar()
        Glide.with(this).load(character.image).centerCrop().centerInside().into(binding.ivExpanded)
        collapsingToolbarControl()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        if (character.isFavorite) {
            menu?.findItem(R.id.actionbar_menu_fav)?.setIcon(R.drawable.ic_fav_on)
        } else {
            menu?.findItem(R.id.actionbar_menu_fav)?.setIcon(R.drawable.ic_fav_off)
        }
        return true
    }

    private fun collapsingToolbarControl() {
        val appBarLayout = binding.abLayout
        appBarLayout.addOnOffsetChangedListener(object : OnOffsetChangedListener {
            var isShow = false
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true
                    binding.toolbar.title = character.name
                } else if (isShow) {
                    isShow = false
                }
            }
        })
    }

    override fun setDescription(description: String) {
        character.description = description
        with(binding.content) {
            tvDescription.show()
            tvDescription.text = description
            llLoading.hide()
            llPlaceholder.hide()
        }
    }

    override fun disableDescription() {
        with(binding.content) {
            llLoading.hide()
            tvDescription.hide()
        }
    }

    override fun showPlaceholder() {
        with(binding.content) {
            llPlaceholder.show()
            btRetry.setOnClickListener {
                presenter.getDescription(character)

            }
        }
    }

    override fun showLoading() {
        with(binding.content) {
            llLoading.show()
            llPlaceholder.hide()
        }
    }

    override fun showError(string: String) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }
}