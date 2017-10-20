package solutions.alterego.android.unisannio.scienze

import org.jsoup.nodes.Document
import solutions.alterego.android.unisannio.models.Article
import solutions.alterego.android.unisannio.interfaces.Parser
import java.util.ArrayList

class ScienzeDetailParser : Parser<String> {

    var bodyelements = ArrayList<String>()

    override fun parse(document: Document): List<String> {

        val newsBodys = document.select("div.item-page")

        for (element in newsBodys) {
            val str = element.select("p")[1].text()

            bodyelements.add(0, str)
        }

        return bodyelements
    }
}