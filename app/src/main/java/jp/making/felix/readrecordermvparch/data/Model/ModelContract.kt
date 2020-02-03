package jp.making.felix.readrecordermvparch.data.Model

import jp.making.felix.readrecordermvparch.data.Book

interface ModelContract {
    interface Repository:BaseRepository{
        fun registData(isbn: String,type: Int):Boolean
    }
    interface RemoteData{
        suspend fun searchData(isbn:String,type: Int):Book
    }
    interface LocalData:BaseRepository{
        fun registData(book:Book):Boolean
    }
}