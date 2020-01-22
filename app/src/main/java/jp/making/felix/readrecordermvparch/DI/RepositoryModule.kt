package jp.making.felix.readrecordermvparch.DI

import dagger.Component

@Component(modules = [DataBaseModule::class])
interface RepositoryModule {
    fun inject()
}