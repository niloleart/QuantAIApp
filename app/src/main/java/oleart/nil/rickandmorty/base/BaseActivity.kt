package oleart.nil.rickandmorty.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import oleart.nil.rickandmorty.R
import oleart.nil.rickandmorty.base.BaseActivity.ActionBarType.DETAIL
import oleart.nil.rickandmorty.base.BaseActivity.ActionBarType.HOME
import oleart.nil.rickandmorty.base.BaseActivity.ActionBarType.NONE
import oleart.nil.rickandmorty.base.BaseActivity.ActionBarType.PROCESS

abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {

    enum class ActionBarType {
        HOME, PROCESS, DETAIL, NONE
    }

    lateinit var binding: B
//    val binding: T by lazy { setViewBinding() }

    protected var activity: Activity? = null

    protected var context: Context? = null

    protected var myToolbar: Toolbar? = null

    abstract fun setViewBinding(): B

    abstract fun setViewGroup(): ViewGroup

    protected abstract fun getTitleActivity(): String

    abstract fun getTypeActionBar(): ActionBarType?

    abstract fun bindToolbar(): Toolbar?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = setViewBinding()
        setContentView(setViewGroup())
        myToolbar = bindToolbar()
        initializeVariables()
        setActionBar()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        supportActionBar?.let { actionBar ->
            when (getTypeActionBar()) {
                HOME -> {
                    menuInflater.inflate(R.menu.home, menu)
                    val item = menu?.findItem(R.id.actionbar_menu_search)
                }
                PROCESS -> {
                    menuInflater.inflate(R.menu.menu_process, menu)
//                    setCancelButton(menu)
                }
                DETAIL -> {
                    menuInflater.inflate(R.menu.menu_detail, menu)
                }
                NONE -> actionBar.hide()
                else -> {
                    //Not needed
                }
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun initializeVariables() {
        context = this
        activity = this
    }

    private fun configureActionBar(actionBar: ActionBar) {
        actionBar.setDisplayUseLogoEnabled(false)
        actionBar.setDisplayShowTitleEnabled(true)
        actionBar.title = getTitleActivity()
    }

    fun refreshTitleActionBar() {
        if (getTypeActionBar() == HOME) {
            val witTitle = !getTitleActivity().isNullOrEmpty()
            if (witTitle) {
                supportActionBar?.let { actionBar ->
                    actionBar.setDisplayUseLogoEnabled(false)
                    actionBar.setDisplayShowHomeEnabled(false)
                    actionBar.setDisplayShowTitleEnabled(true)
                    actionBar.title = getTitleActivity()
                }
            } else {
                supportActionBar?.let { actionBar ->
                    actionBar.setDisplayUseLogoEnabled(true)
                    actionBar.setDisplayShowHomeEnabled(true)
                    actionBar.setDisplayShowTitleEnabled(false)
                }
            }
        } else {
            supportActionBar?.title = getTitleActivity()
        }
    }

    private fun setActionBar() {
        myToolbar?.let {
            setSupportActionBar(it)
        }

        supportActionBar?.let { actionBar ->
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(false)
            actionBar.setHomeButtonEnabled(true)
            when (getTypeActionBar()) {
                HOME -> {
                    actionBar.setDisplayHomeAsUpEnabled(false)
                    actionBar.setDisplayUseLogoEnabled(false)
                    actionBar.setDisplayShowTitleEnabled(true)
                }
                else -> configureActionBar(actionBar)
            }
        }
    }
}