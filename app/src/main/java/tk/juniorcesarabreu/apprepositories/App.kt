package tk.juniorcesarabreu.apprepositories

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import tk.juniorcesarabreu.apprepositories.data.di.DataModule
import tk.juniorcesarabreu.apprepositories.domain.di.DomainModule
import tk.juniorcesarabreu.apprepositories.presentation.di.PresentationModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // inicia o Koin
        startKoin {
            androidContext(this@App)
        }

        // inicia o módulo da injeçao de dependencia definidos utilizando Koin
        DataModule.load()

        // modulo de dependencias de usecases
        DomainModule.load()

        PresentationModule.load()
    }
}