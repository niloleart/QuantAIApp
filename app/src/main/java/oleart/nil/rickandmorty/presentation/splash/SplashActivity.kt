package oleart.nil.rickandmorty.presentation.splash

import android.os.Bundle
import dagger.android.AndroidInjection
import oleart.nil.rickandmorty.MainActivity
import oleart.nil.rickandmorty.base.BaseActivity
import oleart.nil.rickandmorty.base.hide
import oleart.nil.rickandmorty.base.launchProcessActivity
import oleart.nil.rickandmorty.base.show
import oleart.nil.rickandmorty.databinding.ActivitySplashBinding
import oleart.nil.rickandmorty.domain.model.Character
import oleart.nil.rickandmorty.presentation.splash.SplashContract.Presenter
import javax.inject.Inject

class SplashActivity : BaseActivity<ActivitySplashBinding>(), SplashContract.View {

    @Inject
    lateinit var presenter: Presenter

    override fun setViewBinding() = ActivitySplashBinding.inflate(layoutInflater)

    override fun getTitleActivity() = "Splash"

    override fun getTypeActionBar() = ActionBarType.NONE

    override fun setViewGroup() = binding.root

    override fun bindToolbar() = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        presenter.initialize()
    }

    override fun showLoading() {
        binding.llLoading.show()
        binding.llRetry.hide()
    }

    override fun showError() {
        binding.llRetry.show()
    }

    override fun hideLoading() {
        binding.llLoading.hide()
    }

    override fun goToHome(characters: MutableList<Character>) {
        val intent = MainActivity.makeIntent(this, characters)
        launchProcessActivity(intent)
    }
}