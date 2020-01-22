package jp.making.felix.readrecordermvparch.data.Model

import jp.making.felix.readrecordermvparch.data.Book
import javax.inject.Singleton

@Singleton
class RemoteBookModel:ModelContract.RemoteData{
    override suspend fun registData(book: Book) {

    }

    override suspend fun searchData(id: String): Book {

    }
}