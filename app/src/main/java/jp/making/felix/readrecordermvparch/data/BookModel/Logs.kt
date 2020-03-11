package jp.making.felix.readrecordermvparch.data.BookModel

import io.realm.RealmObject

open class Logs(
    var logData:String = ""
):RealmObject()