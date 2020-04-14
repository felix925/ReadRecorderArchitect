package jp.making.felix.readrecordermvparch.data.Repository

import android.util.Log
import jp.making.felix.readrecordermvparch.data.BookModel.Book
import jp.making.felix.readrecordermvparch.data.Model.Remote.RemoteBookModel
import jp.making.felix.readrecordermvparch.data.Repository.Local.LocalBookModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookRepository @Inject constructor(
    val remoteRepo: RemoteBookModel,
    val localRepo: LocalBookModel
):BaseRepository{

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
        }
        else{
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

    override suspend fun searchData(id: String): Book {
        if(isDirty){
            this.cachedData = localRepo.getAllData().toMutableList()
            isDirty = false
        }
        return cachedData.filter { it.id == id }[0]
    }

    override suspend fun updateData(id: String, pageValue: String,thought:String) =
        localRepo.updateData(id,pageValue,thought)

    override suspend fun registBook(book: Book) {
        Log.d("call", book.toString())
        localRepo.registData(book)
    }
}