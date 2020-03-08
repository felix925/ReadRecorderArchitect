package jp.making.felix.readrecordermvparch.DI

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