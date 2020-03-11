package jp.making.felix.readrecordermvparch.DI

import dagger.Module
import dagger.Provides
import jp.making.felix.readrecordermvparch.BookListView.ListViewContract
import jp.making.felix.readrecordermvparch.BookListView.ListViewPresenter
import jp.making.felix.readrecordermvparch.data.Model.BookRepository

@Module
class ListViewModule {
    @Provides
    fun provideListViewPresenter(
        repository: BookRepository
    ): ListViewContract.Presenter = ListViewPresenter(repository)
}