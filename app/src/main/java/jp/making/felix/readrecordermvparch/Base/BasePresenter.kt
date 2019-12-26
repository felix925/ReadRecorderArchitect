package jp.making.felix.readrecordermvparch.Base

import jp.making.felix.readrecordermvparch.data.Book

interface BasePresenter {
    fun start()
    fun getData(count:String):Book
    fun getAllData():List<Book>
    fun registData(name:String,imageURL:String)
}