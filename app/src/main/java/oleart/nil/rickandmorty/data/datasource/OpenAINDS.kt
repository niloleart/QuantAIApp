package oleart.nil.rickandmorty.data.datasource

import android.content.Context
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.client.OpenAI
import oleart.nil.rickandmorty.base.Either
import oleart.nil.rickandmorty.base.errors.DataSourceError
import oleart.nil.rickandmorty.base.errors.RickAndMortyError
import javax.inject.Inject

@OptIn(BetaOpenAI::class)
class OpenAINDS @Inject constructor(
    val context: Context
) : NDS<ChatCompletionRequest, Either<DataSourceError, String>>() {

    override suspend fun getData(request: ChatCompletionRequest): Either<DataSourceError, String> {
        val openAI = OpenAI("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") //TODO do not commit
        return try {
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