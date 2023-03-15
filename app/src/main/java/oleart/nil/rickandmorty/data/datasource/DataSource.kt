package oleart.nil.rickandmorty.data.datasource

interface DataSource<RequestType, ResultType> {

    @Throws(Exception::class)
    suspend fun getData(request: RequestType): ResultType {
        throw NotImplementedError()
    }

    // TODO: create custom exception class
    @Throws(Exception::class)
    fun getData(): ResultType {
        throw NotImplementedError()
    }

    @Throws(Exception::class)
    fun getDataWithParams(params: Map<String, String>): ResultType {
        throw NotImplementedError()
    }
}