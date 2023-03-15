package oleart.nil.rickandmorty.data

import com.aallam.openai.api.BetaOpenAI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import oleart.nil.rickandmorty.base.Either
import oleart.nil.rickandmorty.base.errors.DataSourceError
import oleart.nil.rickandmorty.data.datasource.OpenAINDS
import oleart.nil.rickandmorty.data.requestcreator.ChatGPTRequestCreator
import oleart.nil.rickandmorty.domain.model.Character
import javax.inject.Inject

class OpenAIRepository @Inject constructor(
    private val openAINDS: OpenAINDS
) {

    @OptIn(BetaOpenAI::class)
    suspend fun getDescription(character: Character): Either<DataSourceError, String> =
        withContext(Dispatchers.IO) {
            val request = ChatGPTRequestCreator(character).createRequest()
            openAINDS.getData(request)
        }
}