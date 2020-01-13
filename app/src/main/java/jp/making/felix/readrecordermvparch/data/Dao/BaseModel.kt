package jp.making.felix.readrecordermvparch.data.Dao

import jp.making.felix.readrecordermvparch.data.Book

interface BaseModel {
    fun getAllData():List<Book>
    fun deleteData(id:String)
    fun searchData(id:String): Book
    fun updateData(id:Int,value:String)
    fun registData(isbn:String)
}