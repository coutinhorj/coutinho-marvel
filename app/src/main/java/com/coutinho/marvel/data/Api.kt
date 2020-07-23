package br.com.coutinho.marvel.data

import br.com.coutinho.marvel.data.model.Response
import br.com.coutinho.marvel.infrastructure.BASE_URL
import br.com.coutinho.marvel.infrastructure.PRIVATE_KEY
import br.com.coutinho.marvel.infrastructure.PUBLIC_KEY
import br.com.coutinho.marvel.infrastructure.utilities.md5
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface Api {
    @GET("characters")
    fun allCharacters(@Query("offset") offset: Int? = 10): Observable<Response>

    companion object {
        fun getService(): Api {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)
            httpClient.addInterceptor { chain ->
                val original = chain.request()
                val originalHttpUrl = original.url

                val ts = (Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis / 1000L).toString()
                val url = originalHttpUrl.newBuilder()
                        .addQueryParameter("apikey", PUBLIC_KEY)
                        .addQueryParameter("ts", ts)
                        .addQueryParameter("hash", "$ts$PRIVATE_KEY$PUBLIC_KEY".md5())
                        .build()

                chain.proceed(original.newBuilder().url(url).build())
            }

            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient.build())
                    .build()

            return retrofit.create<Api>(Api::class.java)
        }
    }
}