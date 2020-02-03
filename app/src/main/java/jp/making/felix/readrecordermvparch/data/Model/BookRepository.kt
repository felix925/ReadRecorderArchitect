package jp.making.felix.readrecordermvparch.data.Model

import android.util.Log
import jp.making.felix.readrecordermvparch.data.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Error
import javax.inject.Singleton

@Singleton
class BookRepository(private val localRepo:ModelContract.LocalData, private val remoteRepo:ModelContract.RemoteData):ModelContract.Repository {
    lateinit var cachedData: MutableList<Book>
    var isDirty: Boolean = true
    override fun deleteData(id: String) {
        localRepo.deleteData(id)
        isDirty = true
    }

    override fun getAllData(): List<Book> {
        if ((cachedData.isEmpty()) || isDirty) {
            GlobalScope.launch(Dispatchers.IO) {
                cachedData = localRepo.getAllData().toMutableList()
            }
            isDirty = false
        }
        return cachedData
    }
    
    override fun registData(isbn: String, type: Int): Boolean {
        var isSuccess = false
        if (localRepo.searchData(isbn).id == "NOTFOUND") {
            return isSuccess
        }
        lateinit var result:Book
        GlobalScope.launch(Dispatchers.IO) {
            result = remoteRepo.searchData(isbn, type)
            if (result.id == "ERROR") {
                isSuccess = false
            } else {
                GlobalScope.launch(Dispatchers.Main){localRepo.registData(result)}
                isDirty = true
                isSuccess = true
            }
        }
        Log.i("regist_success_value",isSuccess.toString())
        return isSuccess
    }

    override fun searchData(id: String): Book {
        if((cachedData.isEmpty()) || isDirty){
            this.cachedData = localRepo.getAllData().toMutableList()
            isDirty = false
        }
        return cachedData.filter { it.id == id }[0]
    }

    override fun updateData(id: Int, pageValue: String,thought:String) {
        localRepo.updateData(id,pageValue,thought)
    }
}