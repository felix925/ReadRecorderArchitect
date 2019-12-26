package jp.making.felix.readrecordermvparch.data.Dao

import android.content.res.Resources
import io.realm.Realm
import jp.making.felix.readrecordermvparch.data.Book
import jp.making.felix.readrecordermvparch.data.Page
import java.util.*
import java.text.SimpleDateFormat

class BookDao: BaseDao {
    private val myRealm:Realm
    init{
        myRealm = Realm.getDefaultInstance()
    }
    override fun deleteData(id: String) {
        myRealm.executeTransaction(Realm.Transaction {
            it.where(Book::class.java).equalTo("id",id)
                .findFirst()?.deleteFromRealm()
        })
    }

    override fun getAllData():List<Book> {
        return myRealm.where(Book::class.java).findAll().toList()
    }

    override fun searchData(id: String): Book {
        val result = myRealm.where(Book::class.java).equalTo("id",id).findFirst()
        if(result != null){
            return result
        }
        else{
            throw Resources.NotFoundException("Can't Search : BookData is null RequireId=${id}")
        }
    }

    override fun updateData(id: Int, value: String) {
        val result = myRealm.where(Book::class.java).equalTo("id",id).findFirst()
        if(result != null){
            result.pages.add(Page(value.toInt()))
        }
        else{
            throw Resources.NotFoundException("Can't Update : BookData is null RequireId=${id}")
        }
    }

    override fun registData(name: String, imageURL: String) {
        myRealm.executeTransaction {
            if(it.where(Book::class.java).equalTo("name",name).findFirst() == null) {
                val bookData = myRealm.createObject(Book::class.java, UUID.randomUUID().toString())
                bookData.name = name
                bookData.imageUrl = imageURL
                bookData.lastLog = getDate()
                myRealm.copyToRealm(bookData)
            }
        }
    }

    private fun getDate():String{
        val date = Date()
        val dataFormat = SimpleDateFormat("yyyy/MM/dd",Locale.getDefault())
        return dataFormat.format(date)
    }
}