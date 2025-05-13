package de.syntax_institut.androidabschlussprojekt.domain.model

data class Quote(
    val text: String,
    val author: String,
    val authorInfo: String = "",
    val authorImageResId: Int? = null
)