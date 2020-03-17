package jp.making.felix.readrecordermvparch.DI

import dagger.Module
import dagger.Provides
import jp.making.felix.readrecordermvparch.data.Model.Remote.RemoteBookModel
import jp.making.felix.readrecordermvparch.data.Repository.BookRepository
import jp.making.felix.readrecordermvparch.data.Repository.Local.LocalBookModel
import javax.inject.Singleton

@Module
class DataBaseModule {
    @Singleton
    @Provides
    fun provideLocalRepository(): LocalBookModel = LocalBookModel()

    @Singleton
    @Provides
    fun provideRemoteRepository(): RemoteBookModel = RemoteBookModel()

    @Singleton
    @Provides
    fun provideRepository(
        localRepo:LocalBookModel,
        remoteRepo:RemoteBookModel
    ): BookRepository = BookRepository(remoteRepo,localRepo)
}