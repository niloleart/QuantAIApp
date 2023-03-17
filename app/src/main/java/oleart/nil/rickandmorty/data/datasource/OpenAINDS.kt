package oleart.nil.rickandmorty.data.datasource

import android.content.Context
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.client.OpenAI
import oleart.nil.rickandmorty.base.Either
import oleart.nil.rickandmorty.base.errors.DataSourceError
import oleart.nil.rickandmorty.base.errors.RickAndMortyError
import oleart.nil.rickandmorty.remoteconfiguration.RemoteConfiguration
import javax.inject.Inject

@OptIn(BetaOpenAI::class)
class OpenAINDS @Inject constructor(
    val context: Context,
    private val remoteConfiguration: RemoteConfiguration
) : NDS<ChatCompletionRequest, Either<DataSourceError, String>>() {

    override suspend fun getData(request: ChatCompletionRequest): Either<DataSourceError, String> {
        return try {
            val openAI = OpenAI(remoteConfiguration.getOpenAIKey())
            val completion: ChatCompletion = openAI.chatCompletion(request)
            if (completion.choices[0].message != null && completion.choices[0].message!!.content.isNotEmpty()) {
                Either.Success(completion.choices[0].message!!.content)
            } else {
                Either.Error(DataSourceError(RickAndMortyError()))
            }
        } catch (e: Exception) {
            Either.Error(DataSourceError(RickAndMortyError("There has been an error generating the description")))
        }
    }
}