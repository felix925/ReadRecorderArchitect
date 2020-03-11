package jp.making.felix.readrecordermvparch.data.BookModel

import io.realm.RealmObject

open class Page(
    var pageData:Int = 0
):RealmObject()