package jp.making.felix.readrecordermvparch.data.Model

import android.annotation.SuppressLint
import com.squareup.moshi.Moshi
import io.realm.RealmList
import jp.making.felix.readrecordermvparch.data.Book
import jp.making.felix.readrecordermvparch.data.GoogleBook.GoogleBook
import jp.making.felix.readrecordermvparch.data.Page
import jp.making.felix.readrecordermvparch.data.UpdateDate
import kotlinx.coroutines.*
import okhttp3.*
import java.text.SimpleDateFormat
import java.util.*

class RemoteBookModel:ModelContract.RemoteData{
    override suspend fun searchData(isbn: String, type: Int): Book {
        lateinit var book:Book
        when(type){
            0 -> book = searchFromGoogle(isbn)
            else -> throw Error("BOOK IS UNDEFINED")
        }
        return book
    }
    private suspend fun searchFromGoogle(isbn: String):Book {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://www.googleapis.com/books/v1/volumes?q=isbn:${isbn}")
            .build()
        val moshiAdapter = Moshi.Builder().build().adapter(GoogleBook::class.java)
        return withContext(Dispatchers.IO) {
            val response = client.newCall(request).execute()
            val resString = response.body!!.string()
            val bookData =
                moshiAdapter.fromJson(resString) ?: throw Error("Book is not Found")
            val bookName = bookData.items[0].volumeInfo.title
            val imageURL = bookData.items[0].volumeInfo.imageLinks.thumbnail
            val maxPage = bookData.items[0].volumeInfo.pageCount.toString()
            val updateDate = UpdateDate(getDate())
            Book(
                UUID.randomUUID().toString(),
                isbn,
                bookName,
                imageURL,
                RealmList(updateDate),
                RealmList(Page(0)),
                maxPage,
                false
            )
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getDate():String{
        return SimpleDateFormat("yyyy-MM-dd").toString()
    }
}