package solutions.alterego.android.unisannio.ingegneria

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

import java.util.ArrayList

import javax.inject.Inject

import solutions.alterego.android.unisannio.cercapersone.Person
import solutions.alterego.android.unisannio.interfaces.Parser

class IngegneriaCercapersoneParser @Inject
constructor() : Parser<Person> {

    override fun parse(document: Document): ArrayList<Person> {

        val persons = ArrayList<Person>()

        val elements = document.getElementsByTag("item")

        for (element in elements) {

            val nome = element.getElementsByTag("title").text()
            val ruolo = element.getElementsByTag("position").text()
            val email = element.getElementsByTag("email").text()
            val ufficio = element.getElementsByTag("address").text()
            val telefono = element.getElementsByTag("telephone").text()
            val webPage = element.getElementsByTag("personalwebpage").text()
            val tutoring = element.getElementsByTag("tutoring").text()

            if (nome != null) {
                val person = Person(nome, ruolo, email, telefono, ufficio, webPage, tutoring)
                persons.add(person)
            }
        }
        return persons
    }
}
