package jp.making.felix.readrecordermvparch.data.Model

import android.util.Log
import jp.making.felix.readrecordermvparch.data.Book
import jp.making.felix.readrecordermvparch.data.Model.Local.LocalBookModel
import jp.making.felix.readrecordermvparch.data.Model.Remote.RemoteBookModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Singleton

@Singleton
class BookRepository(private val localRepo:LocalBookModel, private val remoteRepo:RemoteBookModel):BaseRepository{
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
        val books = localRepo.searchData(isbn)
        if (localRepo.searchData(isbn).id != "NOTFOUND") {
            return isSuccess
        }
        lateinit var result:Book
        GlobalScope.launch(Dispatchers.IO) {
            result = remoteRepo.searchData(isbn, type)
            if (result.id == "ERROR") {
                isSuccess = false
            } else {
                localRepo.registData(result)
                isDirty = true
                isSuccess = true
            }
        }
        //TODO コルーチン内で変数書き換えがあった際に結果を返すのをまつ仕様かを再確認し、それがメインスレッドを待たせるようなことがあれば対策を考えて修正する
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