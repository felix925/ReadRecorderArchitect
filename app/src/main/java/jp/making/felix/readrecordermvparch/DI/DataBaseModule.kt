package jp.making.felix.readrecordermvparch.DI

import dagger.Binds
import dagger.BindsInstance
import dagger.Module
import dagger.Provides
import jp.making.felix.readrecordermvparch.BookListView.ListViewContract
import jp.making.felix.readrecordermvparch.BookListView.ListViewPresenter
import jp.making.felix.readrecordermvparch.data.Model.BookRepository
import jp.making.felix.readrecordermvparch.data.Model.Remote.RemoteBookModel
import jp.making.felix.readrecordermvparch.data.Repository.Local.LocalBookModel
import javax.inject.Inject
import javax.inject.Named
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
    fun provideRepository(): BookRepository = BookRepository()
}