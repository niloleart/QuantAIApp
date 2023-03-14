package oleart.nil.rickandmorty

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import oleart.nil.rickandmorty.base.BaseActivity
import oleart.nil.rickandmorty.base.BaseActivity.ActionBarType.HOME
import oleart.nil.rickandmorty.databinding.ActivityMainBinding
import oleart.nil.rickandmorty.domain.model.Characters
import oleart.nil.rickandmorty.ui.dashboard.DashboardFragment
import oleart.nil.rickandmorty.ui.home.HomeFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var characters: Characters
    private lateinit var active: Fragment
    private lateinit var fragmentHome: HomeFragment
    private lateinit var fragmentFavs: DashboardFragment

    companion object {

        private const val EXTRA_CHARACTERS = "EXTRA_CHARACTERS"

        fun makeIntent(context: Context, characters: Characters) =
            Intent(context, MainActivity::class.java)
                .putExtra(EXTRA_CHARACTERS, characters)
    }

    override fun setViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun getTitleActivity() = "Main"

    override fun getTypeActionBar() = HOME

    override fun setViewGroup() = binding.root

    override fun bindToolbar() = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getExtras()
        initFragments()
        setNavigation()
    }

    private fun initFragments() {
        fragmentHome = HomeFragment(characters)
        fragmentFavs = DashboardFragment()
        active = fragmentHome
    }

    private fun setNavigation() {
        val fm = supportFragmentManager
        initFragmentsState(fm)
        binding.navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.page_home -> {
                    navHomeFragment(fm)
                    return@setOnItemSelectedListener true
                }
                R.id.page_favorites -> {
                    navFavsFragment(fm)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    private fun initFragmentsState(fm: FragmentManager) {
        fm.beginTransaction().add(R.id.fl_home, fragmentFavs).hide(fragmentFavs).commit()
        fm.beginTransaction().add(R.id.fl_home, active).commit()
    }

    private fun navHomeFragment(fm: FragmentManager) {
        fm.beginTransaction().hide(active).show(fragmentHome).commit()
        active = fragmentHome
    }

    private fun navFavsFragment(fm: FragmentManager) {
        fm.beginTransaction().hide(active).show(fragmentFavs).commit()
        active = fragmentFavs
    }

    private fun getExtras() {
        characters = intent.getSerializableExtra(EXTRA_CHARACTERS) as Characters
    }

    override fun onBackPressed() {
        if (active is HomeFragment) {
            this.finishAffinity()
        } else {
            navHomeFragment(supportFragmentManager)
        }
    }
}