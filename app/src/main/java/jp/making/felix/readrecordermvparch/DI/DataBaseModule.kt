package jp.making.felix.readrecordermvparch.DI

import dagger.Module
import dagger.Provides
import jp.making.felix.readrecordermvparch.data.Model.BookRepository
import jp.making.felix.readrecordermvparch.data.Model.LocalBookModel
import jp.making.felix.readrecordermvparch.data.Model.RemoteBookModel

//@Module
//class DataBaseModule {
//    @Provides
//    fun provideRepository(): BookRepository{
//        return BookRepository(provideLocalRepository(),provideRemoteRepository())
//    }
//    @Provides
//    fun provideLocalRepository():LocalBookModel{
//        return LocalBookModel()
//    }
//    @Provides
//    fun provideRemoteRepository():RemoteBookModel{
//        return RemoteBookModel()
//    }
//}