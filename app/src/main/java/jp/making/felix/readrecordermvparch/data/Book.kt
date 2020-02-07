package jp.making.felix.readrecordermvparch.data

import androidx.room.Entity
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.io.Serializable

@Entity(tableName = "books")
open class Book(
    @PrimaryKey
    var id: String = "",
    var isbn: String = "",
    var name: String = "",
    var imageUrl: String = "",
    var updateDate: RealmList<UpdateDate> = RealmList(UpdateDate("")),
    var pages: RealmList<Page> = RealmList(Page(0)),
    var maxPage:String = "",
    var alreadyRead: Boolean = false,
    var tag:RealmList<Tag> = RealmList(Tag("ALL")),
    var readLog: RealmList<Logs> = RealmList(Logs(""))
):RealmObject(),Serializable