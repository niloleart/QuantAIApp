package oleart.nil.rickandmorty

import android.content.Context
import io.mockk.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.test.setMain
import oleart.nil.rickandmorty.data.preferences.AppSharedPreferences
import oleart.nil.rickandmorty.domain.DatabaseInteractor
import oleart.nil.rickandmorty.domain.RickAndMortyInteractor
import oleart.nil.rickandmorty.domain.model.Character
import oleart.nil.rickandmorty.ui.home.HomeContract
import oleart.nil.rickandmorty.ui.home.HomePresenter
import org.junit.*
import org.junit.Assert.*
import kotlin.coroutines.CoroutineContext

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class HomePresenterTest : CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main + SupervisorJob()

    lateinit var presenter: HomePresenter

    private lateinit var view: HomeContract.View
    private lateinit var context: Context
    private lateinit var rickAndMortyInteractor: RickAndMortyInteractor
    private lateinit var databaseInteractor: DatabaseInteractor
    private lateinit var appSharedPreferences: AppSharedPreferences

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)

        context = mockk()
        view = mockk()
        rickAndMortyInteractor = mockk(relaxed = true)
        databaseInteractor = mockk(relaxed = true)
        appSharedPreferences = mockk(relaxed = true)

        presenter = HomePresenter(
            view,
            context,
            rickAndMortyInteractor,
            databaseInteractor,
            appSharedPreferences
        )
    }

    @Test
    fun `getLastLoaded page returns 2`() {
        val mockkResult = 2
        every { appSharedPreferences.getLastLoadedPage() } returns mockkResult

        val result = appSharedPreferences.getLastLoadedPage()

        assertTrue(result == 2)
    }

    @Test
    fun `getAllCharacters needs not update, do not call updateRV`() {
        val characters = mockk<MutableList<Character>>(relaxed = true)

        coEvery { databaseInteractor.getStoredCharacters() } returns characters

        presenter.getAllCharacters(characters.toMutableList())

        verify(exactly = 0) { view.updateRV(any(), any()) }
    }

    @Test
    fun `getAllCharacters needs update, call updateRV`() {
        val characters = getCharacters()
        val dbCharacter = getCharacters(true)

        presenter.setInitCharacters(characters.toMutableList())
        coEvery { databaseInteractor.getStoredCharacters() } returns dbCharacter

        presenter.getAllCharacters(characters.toMutableList())

        verify() { view.updateRV(any(), any()) }
    }

    private fun getCharacters(isFavorite: Boolean = false): MutableList<Character> {
        val list = mutableListOf<Character>()
        val char1 = getCharacter()
        char1.isFavorite = isFavorite
        every { char1.id } returns 1

        val char2 = getCharacter()
        char2.description = "hola hola"
        every { char2.id } returns 2

        list.add(char1)
        list.add(char2)

        return list
    }

    private fun getCharacter() = mockk<Character>(relaxed = true)
}