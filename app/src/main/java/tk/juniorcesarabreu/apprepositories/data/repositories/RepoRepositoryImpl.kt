package tk.juniorcesarabreu.apprepositories.data.repositories

import kotlinx.coroutines.flow.flow
import tk.juniorcesarabreu.apprepositories.data.services.GitHubServices

class RepoRepositoryImpl(
    private val service: GitHubServices
) : RepoRepository {

    override suspend fun listRepositories(user: String) = flow {

        val repoList = service.listRepositories(user)
        emit(repoList)

//        try {
//            val repoList = service.listRepositories(user)
//            emit(repoList)
//
//        } catch (ex: HttpException) {
//            throw RemoteException(ex.message())
//        }
    }


}