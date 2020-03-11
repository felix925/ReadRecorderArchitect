package jp.making.felix.readrecordermvparch.data.BookModel

import io.realm.RealmObject

open class Tag(
    var tagName:String = ""
): RealmObject()