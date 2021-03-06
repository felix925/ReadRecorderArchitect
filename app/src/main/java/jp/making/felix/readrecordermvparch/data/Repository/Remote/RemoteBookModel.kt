package jp.making.felix.readrecordermvparch.data.Model.Remote

import android.annotation.SuppressLint
import com.squareup.moshi.Moshi
import io.realm.RealmList
import jp.making.felix.readrecordermvparch.data.BookModel.Book
import jp.making.felix.readrecordermvparch.data.BookModel.Page
import jp.making.felix.readrecordermvparch.data.BookModel.UpdateDate
import jp.making.felix.readrecordermvparch.data.GoogleBook.GoogleBook
import kotlinx.coroutines.*
import okhttp3.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton

@Singleton
class RemoteBookModel {
    suspend fun searchData(isbn: String, type: Int): Book =
        when(type) {
            0 -> searchFromGoogle(isbn)
            else -> Book("UNDEFINED")
        }

    private suspend fun searchFromGoogle(isbn: String): Book {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://www.googleapis.com/books/v1/volumes?q=isbn:${isbn}")
            .build()
        val moshiAdapter = Moshi.Builder().build().adapter(GoogleBook::class.java)
        return withContext(Dispatchers.IO) {
            val response = client.newCall(request).execute()
            val resString = response.body!!.string()
            val bookData = moshiAdapter.fromJson(resString) ?: return@withContext Book("NOTFOUND")
            val bookName = bookData.items[0].volumeInfo.title
            val imageURL = bookData.items[0].volumeInfo.imageLinks.thumbnail
            val maxPage = bookData.items[0].volumeInfo.pageCount.toString()
            val updateDate =
                UpdateDate(
                    getDate()
                )

            Book(
                UUID.randomUUID().toString(),
                isbn,
                bookName,
                imageURL,
                RealmList(updateDate),
                RealmList(
                    Page(
                        0
                    )
                ),
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