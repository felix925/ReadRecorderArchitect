package jp.making.felix.readrecordermvparch.data.Repository

import android.util.Log
import jp.making.felix.readrecordermvparch.data.BookModel.Book
import jp.making.felix.readrecordermvparch.data.Model.Remote.RemoteBookModel
import jp.making.felix.readrecordermvparch.data.Repository.Local.LocalBookModel
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
    
    override suspend fun registData(isbn: String, type: Int): Boolean {
        var isSuccess = false
        if (localRepo.searchData(isbn).id != "NOTFOUND") {
            return isSuccess
        }
        var result: Book = remoteRepo.searchData(isbn, type)
        Log.d("resultData",result.toString())
        if (result.id == "ERROR") {
            isSuccess = false
        } else {
            localRepo.registData(result)
            isDirty = true
            isSuccess = true
        }
        return isSuccess
    }

    override suspend fun searchData(id: String): Book {
        if(isDirty){
            this.cachedData = localRepo.getAllData().toMutableList()
            isDirty = false
        }
        return cachedData.filter { it.id == id }[0]
    }

    override suspend fun updateData(id: String, pageValue: String,thought:String) = localRepo.updateData(id,pageValue,thought)

}