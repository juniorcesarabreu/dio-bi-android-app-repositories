package tk.juniorcesarabreu.apprepositories.data.repositories

import kotlinx.coroutines.flow.Flow
import tk.juniorcesarabreu.apprepositories.data.model.Repo

interface RepoRepository {

    suspend fun listRepositories(user: String): Flow<List<Repo>>
}