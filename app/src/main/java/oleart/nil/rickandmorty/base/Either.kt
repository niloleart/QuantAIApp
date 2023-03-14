package oleart.nil.rickandmorty.base

sealed class Either<out L, out R> {
    data class Error<out L>(val a: L) : Either<L, Nothing>()

    data class Success<out R>(val b: R) : Either<Nothing, R>()

    val isSuccess get() = this is Success<R>
    val isError get() = this is Error<L>

    fun getSuccess(): R? {
        return when (this) {
            is Success -> b
            else -> null
        }
    }

    fun getError(): L? {
        return when (this) {
            is Error -> a
            else -> null
        }
    }

    fun <L> left(a: L) = Error(a)
    fun <R> right(b: R) = Success(b)

    fun either(error: (L) -> Unit, success: (R) -> Unit): Any =
        when (this) {
            is Error -> error(a)
            is Success -> success(b)
        }
}