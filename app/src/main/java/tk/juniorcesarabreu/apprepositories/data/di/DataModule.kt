package tk.juniorcesarabreu.apprepositories.data.di

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tk.juniorcesarabreu.apprepositories.data.repositories.RepoRepository
import tk.juniorcesarabreu.apprepositories.data.repositories.RepoRepositoryImpl
import tk.juniorcesarabreu.apprepositories.data.services.GitHubServices

// configuração de injeção de dependência com koin

object DataModule {

    private const val OK_HTTP = "OkHttp"

    // carrega os módulos
    fun load() {
        loadKoinModules(networkModules() + repositoriesModule())
    }

    private fun networkModules(): Module {

        return module {

            // single = vai ser sempre a mesma instância (singleton), contrário de de factory
            single {

                val interceptor = HttpLoggingInterceptor {
                    Log.d(OK_HTTP, it)
                }
                interceptor.level = HttpLoggingInterceptor.Level.BODY

                OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
            }

            single {
                GsonConverterFactory.create(
                    GsonBuilder().create()
                )
            }

            single {
                createService<GitHubServices>(
                    get(),
                    get()
                ) // Koin é responsável por passar as dependencias
            }
        }
    }

    private fun repositoriesModule(): Module {

        return module {
            single<RepoRepository> { RepoRepositoryImpl(get()) }
        }
    }

    private inline fun <reified T> createService(
        client: OkHttpClient,
        factory: GsonConverterFactory
    ): T {

        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(client)
            .addConverterFactory(factory)
            .build().create(T::class.java)
    }
}