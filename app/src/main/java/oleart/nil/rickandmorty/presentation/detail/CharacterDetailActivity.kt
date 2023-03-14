package oleart.nil.rickandmorty.presentation.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import oleart.nil.rickandmorty.R
import oleart.nil.rickandmorty.base.BaseActivity
import oleart.nil.rickandmorty.databinding.ActivityCharacterDetailBinding
import oleart.nil.rickandmorty.domain.model.Character

class CharacterDetailActivity : BaseActivity<ActivityCharacterDetailBinding>() {

    private lateinit var character: Character

    companion object {

        private const val EXTRA_CHARACTER = "EXTRA_CHARACTER"

        fun makeIntent(context: Context, character: Character): Intent {
            return Intent(context, CharacterDetailActivity::class.java)
                .putExtra(EXTRA_CHARACTER, character)
        }
    }

    override fun setViewBinding() = ActivityCharacterDetailBinding.inflate(layoutInflater)

    override fun setViewGroup() = binding.root

    override fun getTitleActivity() = "Detail"

    override fun getTypeActionBar() = ActionBarType.NONE

    override fun bindToolbar() = null

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_bottom_activity, R.anim.slide_out_bottom_activity)
    }

    private fun getExtras() {
        this.character = intent.getSerializableExtra(EXTRA_CHARACTER) as Character
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getExtras()
        val toolbar = binding.toolbar
//        setSupportActionBar(toolbar)

        Glide.with(this).load(character.image).into(binding.ivExpanded)

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
//                    showOption(R.id.action_info)
                } else if (isShow) {
                    isShow = false
//                    hideOption(R.id.action_info)
                }
            }
        })
    }
}