package jp.making.felix.readrecordermvparch.data.Model

import android.content.res.Resources
import android.util.Log
import com.squareup.moshi.Moshi
import io.realm.ImportFlag
import io.realm.Realm
import jp.making.felix.readrecordermvparch.data.Book
import jp.making.felix.readrecordermvparch.data.GoogleBook.GoogleBook
import jp.making.felix.readrecordermvparch.data.Page
import kotlinx.coroutines.*
import okhttp3.*
import timber.log.Timber
import java.io.IOException
import java.util.*
import java.text.SimpleDateFormat
import javax.inject.Singleton

class LocalBookModel: ModelContract.LocalData {
    private val myRealm:Realm = Realm.getDefaultInstance()

    override suspend fun deleteData(id: String) {
        myRealm.executeTransaction(Realm.Transaction {
            it.where(Book::class.java).equalTo("id",id)
                .findFirst()?.deleteFromRealm()
        })
    }

    override suspend fun getAllData():List<Book> {
        return myRealm.where(Book::class.java).findAll().toList()
    }

    override suspend fun searchData(id: String): Book {
        val result = myRealm.where(Book::class.java).equalTo("id",id).findFirst()
        if(result != null){
            return result
        }
        else{
            throw Resources.NotFoundException("Can't Search : BookData is null RequireId=${id}")
        }
    }

    override suspend fun updateData(id: Int, value: String) {

        GlobalScope.launch {
            launch {
                val result = myRealm.where(Book::class.java).equalTo("id",id).findFirst()
                if(result != null){
                    result.pages.add(Page(value.toInt()))
                }
                else{
                    throw Resources.NotFoundException("Can't Update : BookData is null RequireId=${id}")
                }
            }
        }


    }

    override suspend fun registData(book: Book):Boolean{
        myRealm.copyToRealm(book)
        return true
    }

    private fun registToDatabase(name: String,imageUrl:String,maxPage:String){
        myRealm.executeTransaction {
            val bookData = it.createObject(Book::class.java,UUID.randomUUID().toString())
            bookData.apply {
                this.name = name
                this.imageUrl = imageUrl
                this.maxPage = maxPage
                this.lastLog = getDate()
            }
            it.copyToRealm(bookData)
        }
    }

    private fun getDate():String{
        val date = Date()
        val dataFormat = SimpleDateFormat("yyyy/MM/dd",Locale.getDefault())
        return dataFormat.format(date)
    }

//    private fun getBookForAmazon(isbn: String):List<String>{
//
//    }

    private suspend fun getBookForGoogle(isbn: String):List<String>{
        val client = OkHttpClient()
        val url = "https://www.googleapis.com/books/v1/volumes?q=isbn:$isbn"
        val request = Request.Builder().url(url).build()
        val response = client.newCall(request).execute()
        val body = response.body()?.toString()
        val result = mutableListOf<String>()

        body?.let {
            Log.i("BOOKBODY",body)
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.i("BOOKDATA", e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    //呼び出しに成功した場合中身があることは確実なのでエルビス演算子を用いる。
                    val adapter = Moshi.Builder().build().adapter(GoogleBook::class.java)
                    val books = adapter.fromJson(it)
                    Log.i("BOOKDATA", it)
                    books?.let {
                        result.add(it.items[0].volumeInfo.title)
                        result.add(it.items[0].volumeInfo.pageCount.toString())
                        result.add(it.items[0].volumeInfo.imageLinks.thumbnail)
                    }
                }
            })
        }
        return result
    }
}