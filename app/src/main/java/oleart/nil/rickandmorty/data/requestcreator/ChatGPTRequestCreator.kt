package oleart.nil.rickandmorty.data.requestcreator

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.model.ModelId
import oleart.nil.rickandmorty.domain.model.Character

@OptIn(BetaOpenAI::class)
class ChatGPTRequestCreator(private val character: Character) {

    companion object {

        private const val MODEL_ID = "gpt-3.5-turbo"
        private const val MAX_LENGTH = "240"
    }

    fun createRequest(): ChatCompletionRequest {
        return ChatCompletionRequest(
            model = ModelId(MODEL_ID),
            messages = listOf(
                ChatMessage(
                    role = ChatRole.User,
                    content = getContent()
                )
            )
        )
    }

    private fun getContent() =
        "Give me a description of the character ${character.name}, from Rick and Morty TV series, whose status is ${character.status}, species is ${character.species}, type is ${character.type}, gender is ${character.gender}, origin is ${character.origin.name} and location ${character.location.name}. It must not be longer than $MAX_LENGTH words"
}