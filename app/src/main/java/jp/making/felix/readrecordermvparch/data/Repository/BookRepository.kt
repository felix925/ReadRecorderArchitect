package jp.making.felix.readrecordermvparch.data.Repository

import jp.making.felix.readrecordermvparch.data.BookModel.Book
import jp.making.felix.readrecordermvparch.data.Model.Remote.RemoteBookModel
import jp.making.felix.readrecordermvparch.data.Repository.Local.LocalBookModel
import javax.inject.Inject

class BookRepository @Inject constructor(
    val remoteRepo: RemoteBookModel,
    val localRepo: LocalBookModel
) : BaseRepository {

    lateinit var cachedData: MutableList<Book>
    var isDirty: Boolean = true

    override suspend fun deleteData(id: String) {
        localRepo.deleteData(id)
        isDirty = true
    }

    override suspend fun getAllData(): List<Book> {
        if (isDirty) {
            cachedData = localRepo.getAllData().toMutableList()
            isDirty = false
            return cachedData
        } else {
            return cachedData
        }
    }

    override suspend fun searchBook(isbn: String, type: Int): Book {
        val book = localRepo.searchData(isbn)
        if (book.id != "NOTFOUND") {
            return book
        }
        return remoteRepo.searchData(isbn, 0)
    }

    override suspend fun searchDataByIsbn(isbn: String): Book {
        if (isDirty) {
            this.cachedData = localRepo.getAllData().toMutableList()
            isDirty = false
        }
        val searchData = cachedData.filter { it.isbn == isbn }
        return if (searchData.isEmpty()) Book("NOTFOUND") else searchData[0]
    }

    override suspend fun searchDataById(id: String): Book {
        if (isDirty) {
            this.cachedData = localRepo.getAllData().toMutableList()
            isDirty = false
        }
        val searchData = cachedData.filter { it.id == id }
        return if (searchData.isEmpty()) Book("NOTFOUND") else searchData[0]
    }

    override suspend fun searchDataByName(name: String): Book {
        if (isDirty) {
            this.cachedData = localRepo.getAllData().toMutableList()
            isDirty = false
        }
        val searchData = cachedData.filter { it.name == name }
        return if (searchData.isEmpty()) Book("NOTFOUND") else searchData[0]
    }

    override suspend fun updateData(id: String, pageValue: String, thought: String) =
        localRepo.updateData(id, pageValue, thought)

    override suspend fun registBook(book: Book) {
        if (searchDataByIsbn(book.isbn).id == "NOTFOUND") {
            isDirty = true
            // 0,1,2,3となるため衝突が起こらない
            val size = if (cachedData.isEmpty()) 0 else cachedData.size
            localRepo.registData(book, size)
        }
    }

    override suspend fun updateReadFlag(id: String) {
        localRepo.updateReadFlag(id)
    }
}