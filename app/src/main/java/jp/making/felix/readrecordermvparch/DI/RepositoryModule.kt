package jp.making.felix.readrecordermvparch.DI

import dagger.Binds
import dagger.Module
import dagger.Provides
import jp.making.felix.readrecordermvparch.data.BookModel.Book
import jp.making.felix.readrecordermvparch.data.Repository.BaseRepository
import jp.making.felix.readrecordermvparch.data.Repository.BookRepository
import jp.making.felix.readrecordermvparch.data.Repository.Remote.RemoteBookModel
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideBookRepository():BookRepository = BookRepository(RemoteBookModel())
}