package jp.making.felix.readrecordermvparch.BookListView

import jp.making.felix.readrecordermvparch.data.Book

interface BookListViewProtocol {
    fun readData()
    fun navigateDetail(entity: Book)
}