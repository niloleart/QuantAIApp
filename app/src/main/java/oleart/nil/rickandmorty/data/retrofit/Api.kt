package oleart.nil.rickandmorty.data.retrofit

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Api<T> {

    companion object {

        const val DEFAULT_TIMEOUT = 30
    }

    fun createApi(
        interfaceClass: Class<T>,
        context: Context,
        timeout: Int = DEFAULT_TIMEOUT,
        url: String
    ): T {
        val gson = getGson()
        val converter = GsonConverterFactory.create(gson)
        val retrofit = getRetrofit(context, timeout, converter)
        return retrofit.create(interfaceClass)
    }

    private fun getGson(): Gson {
        return GsonBuilder().create()
    }

    private fun getRetrofit(context: Context, timeout: Int, factory: Converter.Factory? = null): Retrofit {
        val converterFactory = factory ?: GsonConverterFactory.create(GsonBuilder().create())
        return Retrofit.Builder()
            .addConverterFactory(converterFactory)
            .baseUrl("https://rickandmortyapi.com/")
            .client(getOkHttpClient(context, timeout))
            .build()
    }

    private fun getOkHttpClient(context: Context, timeout: Int?): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(getLoggingInterceptor())
            .build()
    }

    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }
}