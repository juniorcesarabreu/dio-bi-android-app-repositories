package tk.juniorcesarabreu.apprepositories.domain.di

import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import tk.juniorcesarabreu.apprepositories.domain.ListUserRepositoriesUseCase

object DomainModule {

    fun load() {
        loadKoinModules(useCaseModule())
    }

    private fun useCaseModule(): Module {

        return module {

            // toda ver que precisar de um usecase, será reinstanciado, ao contrário de single
            factory {
                ListUserRepositoriesUseCase(get())
            }
            // poderia ter outros usecases aqui
            // factory { AnotherUseCase(get()) }
        }
    }
}