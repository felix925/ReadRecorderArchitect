package jp.making.felix.readrecordermvparch.data.Model

import jp.making.felix.readrecordermvparch.data.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Singleton

@Singleton
class BookRepository(val localRepo:ModelContract.LocalData,val remoteRepo:ModelContract.RemoteData):ModelContract.Repository {
    lateinit var cachedData: MutableList<Book>
    var isDirty: Boolean = true
    override suspend fun deleteData(id: String) {
        localRepo.deleteData(id)
        isDirty = true
    }

    override suspend fun getAllData(): List<Book> {
        if ((cachedData.isEmpty()) || isDirty) {
            this.cachedData = localRepo.getAllData().toMutableList()
            isDirty = false
        }
        return cachedData
    }
    
    override fun registData(isbn: String, type: Int): Boolean {
        var isSucces = true
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val result = remoteRepo.searchData(isbn, type)
                localRepo.registData(result)
                isDirty = true
            }
            catch (e:Exception){
                isSucces = false
            }
        }
        return isSucces
    }

    override suspend fun searchData(id: String): Book {
        if((cachedData.isEmpty()) || isDirty){
            this.cachedData = localRepo.getAllData().toMutableList()
            isDirty = false
        }
        return cachedData.filter { it.id == id }[0]
    }

    override suspend fun updateData(id: Int, value: String) {

    }
}