package tk.juniorcesarabreu.apprepositories.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import tk.juniorcesarabreu.apprepositories.data.model.Repo
import tk.juniorcesarabreu.apprepositories.domain.ListUserRepositoriesUseCase

class MainViewModel(
    private val listUserRepositoriesUseCase: ListUserRepositoriesUseCase
) : ViewModel() {

    private val _repos = MutableLiveData<State>()
    val repos: LiveData<State> = _repos

    fun getRepoList(user: String) {
        viewModelScope.launch {

            //listUserRepositoriesUseCase.execute(user)
            listUserRepositoriesUseCase(user)
                .onStart {
                    // quando está inciando
                    _repos.postValue(State.Loading)
                }
                .catch {
                    // quando ocorrer alguma exceção
                    _repos.postValue(State.Error(it))
                }
                .collect {
                    // quando finalizar
                    _repos.postValue(State.Success(it))
                }
        }


    }

    sealed class State {
        object Loading : State()
        data class Success(val list: List<Repo>) : State()
        data class Error(val error: Throwable) : State()
    }
}