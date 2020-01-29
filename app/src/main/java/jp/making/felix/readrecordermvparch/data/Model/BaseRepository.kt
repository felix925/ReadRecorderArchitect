package jp.making.felix.readrecordermvparch.data.Model

import jp.making.felix.readrecordermvparch.data.Book

interface BaseRepository {
    suspend fun getAllData():List<Book>
    suspend fun deleteData(id:String)
    suspend fun searchData(id:String): Book
    suspend fun updateData(id:Int,value:String)
}