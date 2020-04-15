package jp.making.felix.readrecordermvparch.data.Repository.Local

import io.realm.Realm
import jp.making.felix.readrecordermvparch.data.BookModel.Book
import jp.making.felix.readrecordermvparch.data.BookModel.Logs
import jp.making.felix.readrecordermvparch.data.BookModel.Page
import jp.making.felix.readrecordermvparch.data.BookModel.UpdateDate
import java.text.SimpleDateFormat
import java.util.*

class LocalBookModel {
    private companion object realm {
        val myRealm: Realm = Realm.getDefaultInstance()
    }

    fun deleteData(id: String) {
        myRealm.executeTransaction {
            it.where(Book::class.java).equalTo("id", id)
                .findFirst()?.deleteFromRealm()
        }
    }

    fun getAllData(): List<Book> = myRealm.where(Book::class.java).findAll().toList()

    fun searchData(id: String): Book = myRealm.where(
        Book::class.java
    ).equalTo("isbn", id).findFirst() ?: Book(
        "NOTFOUND"
    )

    fun updateData(id: String, pageValue: String, thought: String): Boolean {
        var isSuccess: Boolean = false
        myRealm.executeTransaction {
            val result = myRealm.where(Book::class.java).equalTo("id", id).findFirst()
            if (result != null) {
                result.pages.add(
                    Page(
                        pageValue.toInt()
                    )
                )
                result.readLog.add(
                    Logs(
                        thought
                    )
                )
                isSuccess = true
            }
        }
        return isSuccess
    }

    fun registData(book: Book, size: Int) {
        myRealm.executeTransaction {
            val bookData = myRealm.createObject(Book::class.java, size.toString())
            bookData.name = book.name
            bookData.isbn = book.isbn
            bookData.imageUrl = book.imageUrl
            bookData.maxPage = book.maxPage
            bookData.updateDate.add(
                UpdateDate(
                    getDate()
                )
            )
            bookData.maxPage = book.maxPage
            bookData.alreadyRead = false
            myRealm.copyToRealm(bookData)
        }
    }

    fun updateReadFlag(id: String) {
        myRealm.executeTransaction {
            val result = myRealm.where(Book::class.java).equalTo("id", id).findFirst()
            if (result != null) {
                result.alreadyRead = !result.alreadyRead
            }
        }
    }

    private fun getDate(): String {
        val date = Date()
        val dataFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        return dataFormat.format(date)
    }
}