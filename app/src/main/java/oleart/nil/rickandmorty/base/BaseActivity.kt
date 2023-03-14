package oleart.nil.rickandmorty.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import oleart.nil.rickandmorty.base.BaseActivity.ActionBarType.DEFAULT
import oleart.nil.rickandmorty.base.BaseActivity.ActionBarType.HOME
import oleart.nil.rickandmorty.base.BaseActivity.ActionBarType.NONE
import oleart.nil.rickandmorty.base.BaseActivity.ActionBarType.PROCESS

abstract class BaseActivity<T> : AppCompatActivity() {

    enum class ActionBarType {
        HOME, PROCESS, DEFAULT, NONE
    }

    val binding: T by lazy { setViewBinding() }

    protected var activity: Activity? = null

    protected var context: Context? = null

    protected var myToolbar: Toolbar? = null

    abstract fun setViewBinding(): T

    abstract fun setViewGroup(): ViewGroup

    protected abstract fun getTitleActivity(): String

    abstract fun getTypeActionBar(): ActionBarType?

    abstract fun bindToolbar(): Toolbar?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setViewGroup())
        myToolbar = bindToolbar()
        initializeVariables()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        supportActionBar?.let { actionBar ->
            when (getTypeActionBar()) {
                HOME -> {}
                PROCESS -> {}
                DEFAULT -> {}
                NONE -> actionBar.hide()
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onStart() {
        super.onStart()
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

    private fun setActionBar() {
        myToolbar?.let {
            setSupportActionBar(it)
        }

        supportActionBar?.let { actionBar ->
            configureActionBar(actionBar)
        }
    }
}