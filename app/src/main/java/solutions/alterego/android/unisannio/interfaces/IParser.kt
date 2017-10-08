package solutions.alterego.android.unisannio.interfaces

import org.jsoup.nodes.Document

interface IParser<T> {
    fun parse(document: Document): List<T>
}
