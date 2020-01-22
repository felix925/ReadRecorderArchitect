package jp.making.felix.readrecordermvparch.data

import io.realm.RealmObject

open class Logs(
    var logData:String = ""
):RealmObject()