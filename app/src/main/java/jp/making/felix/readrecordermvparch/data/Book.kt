package jp.making.felix.readrecordermvparch.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.io.Serializable

@Entity(tableName = "books")
open class Book(
    @PrimaryKey
    @ColumnInfo(name = "bookid")
    var id: String = "",
    @Required
    @ColumnInfo(name = "bookname")
    var name: String = "",
    var imageUrl: String = "",
    var lastLog: String = "",
    var pages: RealmList<Page> = RealmList(Page(0)),
    var alreadyRead: Boolean = false,
    var tag:RealmList<Tag> = RealmList(Tag("ALL"))
):RealmObject(),Serializable