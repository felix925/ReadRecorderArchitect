package jp.making.felix.readrecordermvparch.data.Model

import jp.making.felix.readrecordermvparch.data.Book

interface BaseModel {
    suspend fun searchData(id:String):Book
}