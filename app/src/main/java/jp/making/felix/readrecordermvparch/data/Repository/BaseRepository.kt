package jp.making.felix.readrecordermvparch.data.Repository

import jp.making.felix.readrecordermvparch.data.BookModel.Book

interface BaseRepository {
    suspend fun getAllData(): List<Book>
    suspend fun deleteData(id: String)
    suspend fun searchDataByIsbn(isbn: String): Book
    suspend fun searchDataById(id: String): Book
    suspend fun updateData(id: String, pageValue: String, thought: String): Boolean
    suspend fun searchBook(isbn: String, type: Int): Book
    suspend fun registBook(book: Book)
    suspend fun updateReadFlag(id: String)
}