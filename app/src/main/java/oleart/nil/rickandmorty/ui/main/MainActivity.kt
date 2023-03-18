package oleart.nil.rickandmorty.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import dagger.android.AndroidInjection
import oleart.nil.rickandmorty.R
import oleart.nil.rickandmorty.R.id
import oleart.nil.rickandmorty.base.BaseActivity
import oleart.nil.rickandmorty.base.BaseActivity.ActionBarType.NONE
import oleart.nil.rickandmorty.databinding.ActivityMainBinding
import oleart.nil.rickandmorty.domain.model.Character
import oleart.nil.rickandmorty.ui.favs.FavsFragment
import oleart.nil.rickandmorty.ui.home.HomeFragment
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var characters: List<Character>
    private lateinit var active: Fragment
    private lateinit var fragmentHome: HomeFragment
    private lateinit var fragmentFavs: FavsFragment
    private lateinit var menu: Menu

    @Inject
    lateinit var presenter: MainActivityContract.Presenter

    companion object {

        private const val EXTRA_CHARACTERS = "EXTRA_CHARACTERS"
        private const val TAG_FAVS_FRAGMENT = "FAV_FRAGMENT"
        private const val TAG_HOME_FRAGMENT = "HOME_FRAGMENT"

        fun makeIntent(context: Context, characters: MutableList<Character>) =
            Intent(context, MainActivity::class.java)
                .putParcelableArrayListExtra(EXTRA_CHARACTERS, characters as ArrayList<out Parcelable>)
    }

    override fun setViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun getTitleActivity() = "Character List"

    override fun getTypeActionBar() = NONE

    override fun setViewGroup() = binding.root

    override fun bindToolbar() = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        refreshTitleActionBar()
        getExtras()
        initFragments()
        setNavigation()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu!!
        return super.onCreateOptionsMenu(menu)
    }

    private fun initFragments() {
        fragmentHome = HomeFragment(characters.toMutableList())
        fragmentFavs = FavsFragment()
        active = fragmentHome
    }

    private fun setNavigation() {
        val fm = supportFragmentManager
        initFragmentsState(fm)
        binding.navView.setOnItemSelectedListener {
            when (it.itemId) {
                id.page_home -> {
                    navHomeFragment(fm)
                    return@setOnItemSelectedListener true
                }
                id.page_favorites -> {
                    navFavsFragment(fm)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    private fun initFragmentsState(fm: FragmentManager) {
        fm.beginTransaction().add(id.fl_home, fragmentFavs, TAG_FAVS_FRAGMENT).hide(fragmentFavs).commit()
        fm.beginTransaction().add(id.fl_home, active, TAG_HOME_FRAGMENT).commit()
    }

    private fun navHomeFragment(fm: FragmentManager) {
        supportActionBar.let {
            menuInflater.inflate(R.menu.toolbar_home, menu)
        }

        fm.beginTransaction().hide(active).show(fragmentHome).commit()
        active = fragmentHome

        val currentFragment = fm.findFragmentByTag(TAG_HOME_FRAGMENT)
        val fragmentTransaction = fm.beginTransaction()
        if (currentFragment != null) {
            with(fragmentTransaction) {
                detach(currentFragment)
                attach(currentFragment)
                commit()
            }
        }
    }

    private fun navFavsFragment(fm: FragmentManager) {
        supportActionBar.let {
            menuInflater.inflate(R.menu.toolbar_detail, menu)
        }

        fm.beginTransaction().hide(active).show(fragmentFavs).commit()
        active = fragmentFavs

        val currentFragment = fm.findFragmentByTag(TAG_FAVS_FRAGMENT)
        val fragmentTransaction = fm.beginTransaction()
        if (currentFragment != null) {
            with(fragmentTransaction) {
                detach(currentFragment)
                attach(currentFragment)
                commit()
            }
        }
    }

    private fun getExtras() {
        characters = intent.getSerializableExtra(EXTRA_CHARACTERS) as MutableList<Character>
    }

    override fun onBackPressed() {
        if (active is HomeFragment) {
            this.finishAffinity()
        } else {
            navHomeFragment(supportFragmentManager)
        }
    }
}