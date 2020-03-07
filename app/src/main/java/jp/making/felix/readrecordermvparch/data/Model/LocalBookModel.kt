package jp.making.felix.readrecordermvparch.data.Model

import io.realm.Realm
import jp.making.felix.readrecordermvparch.data.Book
import jp.making.felix.readrecordermvparch.data.Logs
import jp.making.felix.readrecordermvparch.data.Page
import jp.making.felix.readrecordermvparch.data.UpdateDate
import java.util.*
import java.text.SimpleDateFormat

class LocalBookModel: ModelContract.LocalData {
    private companion object realm{
         val myRealm:Realm = Realm.getDefaultInstance()
    }
    override fun deleteData(id: String) {
        myRealm.executeTransaction {
            it.where(Book::class.java).equalTo("id",id)
                .findFirst()?.deleteFromRealm()
        }
    }

    override fun getAllData():List<Book> {
        return myRealm.where(Book::class.java).findAll().toList()
    }

    override fun searchData(id: String): Book {
        val result = myRealm.where(Book::class.java).equalTo("isbn",id).findFirst()
        if(result != null){
            return result
        }
        else{
            return Book("NOTFOUND")
        }
    }

    override fun updateData(id: String,pageValue: String, thought: String) {
        myRealm.executeTransaction{
            val result = myRealm.where(Book::class.java).equalTo("id",id).findFirst()
            if(result != null){
                result.pages.add(Page(pageValue.toInt()))
                result.readLog.add(Logs(thought))
            }
            else{
                throw NoClassDefFoundError("MISSING BOOK")
            }
        }
    }

    override fun registData(book: Book):Boolean{
        myRealm.executeTransaction{
            val bookData = myRealm.createObject(Book::class.java,UUID.randomUUID().toString())
            bookData.name = book.name
            bookData.isbn = book.isbn
            bookData.imageUrl = book.imageUrl
            bookData.maxPage = book.maxPage
            bookData.updateDate.add(UpdateDate(getDate()))
            bookData.maxPage = book.maxPage
            bookData.alreadyRead = false
            myRealm.copyToRealm(bookData)
        }
        return true
    }

    private fun getDate():String{
        val date = Date()
        val dataFormat = SimpleDateFormat("yyyy/MM/dd",Locale.getDefault())
        return dataFormat.format(date)
    }
}