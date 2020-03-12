package jp.making.felix.readrecordermvparch.DI

import dagger.Module
import dagger.Provides
import jp.making.felix.readrecordermvparch.BookDataRegist.DataRegistContract
import jp.making.felix.readrecordermvparch.BookDataRegist.DataRegistPresenter
import jp.making.felix.readrecordermvparch.BookDataUpdate.DataUpdateContract
import jp.making.felix.readrecordermvparch.BookDataUpdate.DataUpdatePresenter
import jp.making.felix.readrecordermvparch.BookDataView.DataViewContract
import jp.making.felix.readrecordermvparch.BookDataView.DataViewPresenter
import jp.making.felix.readrecordermvparch.BookListView.ListViewContract
import jp.making.felix.readrecordermvparch.BookListView.ListViewPresenter
import jp.making.felix.readrecordermvparch.data.Model.BookRepository

@Module
class PresenterModule {
    @Provides
    fun provideListViewPresenter(
        repository: BookRepository
    ): ListViewContract.Presenter = ListViewPresenter(repository)
    @Provides
    fun provideDataViewPresenter(
        repository: BookRepository
    ): DataViewContract.Presenter = DataViewPresenter(repository)
    @Provides
    fun provideDataUpdatePresenter(
        repository: BookRepository
    ): DataUpdateContract.Presenter = DataUpdatePresenter(repository)
    @Provides
    fun provideDataRegistPresenter(
        repository: BookRepository
    ): DataRegistContract.Presenter = DataRegistPresenter(repository)
}