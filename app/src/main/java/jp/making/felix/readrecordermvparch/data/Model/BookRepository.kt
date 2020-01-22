package jp.making.felix.readrecordermvparch.data.Model

import jp.making.felix.readrecordermvparch.data.Book
import javax.inject.Singleton

@Singleton
class BookRepository(val localRepo:ModelContract.LocalData,val remoteRepo:ModelContract.RemoteData):ModelContract.Repository {
    lateinit var cachedData: MutableList<Book>
    var isDirty: Boolean = true
    override suspend fun deleteData(id: String) {

    }

    override suspend fun getAllData(): List<Book> {

    }

    override suspend fun registData(isbn: String, type: Int): Boolean {

    }

    override suspend fun searchData(id: String): Book {

    }

    override suspend fun updateData(id: Int, value: String) {

    }
}