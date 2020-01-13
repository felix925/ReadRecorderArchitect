package jp.making.felix.readrecordermvparch.data

import io.realm.RealmObject

open class Tag(
    var tagName:String = ""
): RealmObject()