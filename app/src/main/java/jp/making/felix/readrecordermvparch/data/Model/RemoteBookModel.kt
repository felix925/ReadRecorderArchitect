package jp.making.felix.readrecordermvparch.data.Model

import com.squareup.moshi.Moshi
import io.realm.RealmList
import jp.making.felix.readrecordermvparch.data.Book
import jp.making.felix.readrecordermvparch.data.GoogleBook.GoogleBook
import jp.making.felix.readrecordermvparch.data.Page
import kotlinx.coroutines.handleCoroutineException
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton

class RemoteBookModel:ModelContract.RemoteData{
    override suspend fun searchData(isbn: String, type: Int): Book {
        lateinit var book: Book
        when(type){
            0 -> book = searchFromGoogle(isbn)
            else -> throw Error("BOOK IS UNDEFINED")
        }
        return book
    }
    fun searchFromGoogle(isbn: String):Book{
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://www.googleapis.com/books/v1/volumes?q=isbn:{${isbn}}")
            .build()
        val moshiAdapter = Moshi.Builder().build().adapter(GoogleBook::class.java)
        lateinit var book:Book
        client.newCall(request).enqueue(object : okhttp3.Callback{
            @Throws(IOException::class)
            override fun onResponse(call: Call,response: Response){
                val resString = response.body()?.string()!!
                val bookData = moshiAdapter.fromJson(resString) ?: throw Error("Book is not Found")
                bookData.items[0].apply {
                    val bookName = this.volumeInfo.title
                    val imageURL = this.volumeInfo.imageLinks.toString()
                    val maxPage = this.volumeInfo.pageCount.toString()
                    val lastLog = getDate()
                    book = Book(UUID.randomUUID().toString(),bookName,imageURL,lastLog,
                        RealmList(Page(0)),maxPage,false)
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                throw Error("Request is failed")
            }
        })
        return book
    }
    private fun getDate():String{
        return SimpleDateFormat("yyyy-MM-dd").toString()
    }
}