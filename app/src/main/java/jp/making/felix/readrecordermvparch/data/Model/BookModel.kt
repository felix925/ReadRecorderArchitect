package jp.making.felix.readrecordermvparch.data.Model

import android.content.res.Resources
import android.util.Log
import com.squareup.moshi.Moshi
import io.realm.Realm
import jp.making.felix.readrecordermvparch.data.Book
import jp.making.felix.readrecordermvparch.data.GoogleBook.GoogleBook
import jp.making.felix.readrecordermvparch.data.Page
import okhttp3.*
import java.io.IOException
import java.util.*
import java.text.SimpleDateFormat
import kotlin.math.max

class BookModel: BaseModel {
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

    override fun registData(isbn: String,type:Int):Boolean{
        var result = mutableListOf<String>()
        when(type){
            1 -> result = getBookForGoogle(isbn).toMutableList()
//            2 -> result = getBookForAmazon(isbn)
        }
        //データ取得失敗
        if (result[0] != "") {
            registToDatabase(result[0], result[1], result[2])
            return true
        }
        else{
            return false
        }
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

    private fun getBookForGoogle(isbn: String):List<String>{
        val client = OkHttpClient()
        val url = "https://www.googleapis.com/books/v1/volumes?q=isbn:{$isbn}"
        val req = Request.Builder().url(url).build()
        val result = mutableListOf<String>()

        client.newCall(req).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.i("BOOKDATA",e.toString())
            }
            override fun onResponse(call: Call, response: Response) {
                //呼び出しに成功した場合中身があることは確実なのでエルビス演算子を用いる。
                val body = response.body()!!.string()
                val adapter = Moshi.Builder().build().adapter(GoogleBook::class.java)
                val books = adapter.fromJson(body)
                books?.let{
                    result.add(it.items[0].volumeInfo.title)
                    result.add(it.items[0].volumeInfo.pageCount.toString())
                    result.add(it.items[0].volumeInfo.imageLinks.thumbnail)
                }
            }
        })
        return result
    }
}