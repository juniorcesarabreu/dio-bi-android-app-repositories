package tk.juniorcesarabreu.apprepositories.data.services

import retrofit2.http.GET
import retrofit2.http.Path
import tk.juniorcesarabreu.apprepositories.data.model.Repo

interface GitHubServices {

    // suspend = a função será executada em outra thread

    @GET("users/{user}/repos")
    suspend fun listRepositories(@Path("user") user: String): List<Repo>
}