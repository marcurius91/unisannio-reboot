package solutions.alterego.android.unisannio.interfaces

import org.jsoup.nodes.Document

interface Parser<T> {
    fun parse(document: Document): List<T>
}
