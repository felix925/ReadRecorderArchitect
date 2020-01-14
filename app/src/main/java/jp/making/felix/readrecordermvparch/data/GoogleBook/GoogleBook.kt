package jp.making.felix.readrecordermvparch.data.GoogleBook

data class GoogleBook(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)