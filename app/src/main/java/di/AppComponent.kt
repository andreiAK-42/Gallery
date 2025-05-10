package di

import dagger.Component
import viewModel.mainViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [appModule::class])
interface AppComponent {
    fun inject(mainViewModel: mainViewModel)
}