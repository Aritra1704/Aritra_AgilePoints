package com.example.aritra_agilepoint.modules

import com.example.aritra_agilepoint.viewmodel.BaseVM
import com.example.aritra_agilepoint.viewmodel.EmployeeVM
import dagger.Component
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [RetrofitModule::class])
interface AppComponent {
    /**
     * Injects required dependencies into the specified EmployeeVM.
     * @param beerVM EmployeeVM in which to inject the dependencies
     */
    abstract fun inject(beerVM: BaseVM)


    @Component.Builder
    interface Builder {
        fun build(): AppComponent
        fun networkModule(networkModule: RetrofitModule): Builder
    }


}