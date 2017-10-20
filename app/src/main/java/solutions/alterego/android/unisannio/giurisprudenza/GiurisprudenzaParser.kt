package solutions.alterego.android.unisannio.giurisprudenza

import org.jsoup.nodes.Document
import java.util.ArrayList
import java.util.UUID

import solutions.alterego.android.unisannio.interfaces.Parser
import solutions.alterego.android.unisannio.models.Article

class GiurisprudenzaParser : Parser<Article> {

    override fun parse(document: Document): ArrayList<Article> {

        val list = ArrayList<Article>()

        val elements = document.select("item")


        for (element in elements) {
            val title_temp = element.select("title").text();

            var title: String = "title"

            if (title_temp.contains("[Comunicazioni] ")){
                title = title_temp.removePrefix("[Comunicazioni] ")
            }

            else if (title_temp.contains("[Giurisprudenza] ")){
                title = title_temp.removePrefix("[Giurisprudenza] ")
            }

            val body = element.select("description").text()
            val author = element.select("author").text()

            list.add(Article(UUID.randomUUID().toString(), title, author, null, body, null))
        }

        return list
    }
}
