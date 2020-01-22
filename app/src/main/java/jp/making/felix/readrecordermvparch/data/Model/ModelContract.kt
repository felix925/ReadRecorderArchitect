package jp.making.felix.readrecordermvparch.data.Model

import jp.making.felix.readrecordermvparch.data.Book

interface ModelContract {
    interface Repository:BaseRepository
    interface RemoteData:BaseModel{
        override suspend fun searchData(id: String): Book
        suspend fun registData(book: Book)
    }
    interface LocalData:BaseRepository{
        override suspend fun searchData(id: String): Book
    }
}