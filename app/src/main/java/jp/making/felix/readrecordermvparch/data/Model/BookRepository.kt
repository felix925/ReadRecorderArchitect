package jp.making.felix.readrecordermvparch.data.Model

import jp.making.felix.readrecordermvparch.data.Book
import jp.making.felix.readrecordermvparch.data.Model.Local.LocalBookModel
import jp.making.felix.readrecordermvparch.data.Model.Remote.RemoteBookModel

class BookRepository():BaseRepository{
    private lateinit var cachedData: MutableList<Book>
    private val localRepo:LocalBookModel = LocalBookModel()
    private val remoteRepo:RemoteBookModel = RemoteBookModel()
    private var isDirty: Boolean = true

    override fun deleteData(id: String) {
        localRepo.deleteData(id)
        isDirty = true
    }

    override fun getAllData(): List<Book> {
        if (isDirty) {
            cachedData = localRepo.getAllData().toMutableList()
            isDirty = false
        }
        return cachedData
    }
    
    override suspend fun registData(isbn: String, type: Int): Boolean {
        var isSuccess = false
        if (localRepo.searchData(isbn).id != "NOTFOUND") {
            return isSuccess
        }
        val result = remoteRepo.searchData(isbn,0)
        if (result.id == "ERROR") {
            isSuccess = false
        } else {
            localRepo.registData(result)
            isDirty = true
            isSuccess = true
        }
        return isSuccess
    }

    override fun searchData(id: String): Book {
        if(isDirty){
            this.cachedData = localRepo.getAllData().toMutableList()
            isDirty = false
        }
        return cachedData.filter { it.id == id }[0]
    }

    override fun updateData(id: String, pageValue: String,thought:String) {
        localRepo.updateData(id,pageValue,thought)
    }
}