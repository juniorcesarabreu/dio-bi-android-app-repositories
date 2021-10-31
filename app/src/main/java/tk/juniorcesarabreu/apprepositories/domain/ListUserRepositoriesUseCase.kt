package tk.juniorcesarabreu.apprepositories.domain

import kotlinx.coroutines.flow.Flow
import tk.juniorcesarabreu.apprepositories.core.UseCase
import tk.juniorcesarabreu.apprepositories.data.model.Repo
import tk.juniorcesarabreu.apprepositories.data.repositories.RepoRepository

class ListUserRepositoriesUseCase(
    private val repository: RepoRepository
) : UseCase<String, List<Repo>>() {

    override suspend fun execute(param: String): Flow<List<Repo>> {
        return repository.listRepositories(param)
    }
}