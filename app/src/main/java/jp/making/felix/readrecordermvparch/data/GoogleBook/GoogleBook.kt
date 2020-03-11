package jp.making.felix.readrecordermvparch.data.GoogleBook

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GoogleBook(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)