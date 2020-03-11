package jp.making.felix.readrecordermvparch.DI

import dagger.Binds
import dagger.Module
import dagger.Provides
import jp.making.felix.readrecordermvparch.data.Model.Remote.RemoteBookModel
import jp.making.felix.readrecordermvparch.data.Repository.BookRepository
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideBookRepository():BookRepository = BookRepository(RemoteBookModel())
}