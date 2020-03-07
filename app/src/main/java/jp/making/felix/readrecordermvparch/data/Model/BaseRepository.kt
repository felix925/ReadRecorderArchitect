package jp.making.felix.readrecordermvparch.data.Model

import jp.making.felix.readrecordermvparch.data.Book

interface BaseRepository {
    fun getAllData():List<Book>
    fun deleteData(id:String)
    fun searchData(id:String): Book
    fun updateData(id:String,pageValue:String,thought:String)
}