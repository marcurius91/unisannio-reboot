package solutions.alterego.android.unisannio.ingegneria;

import android.util.Log;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import solutions.alterego.android.unisannio.cercapersone.Person;
import solutions.alterego.android.unisannio.interfaces.IParser;

public class IngegneriaCercapersoneParser implements IParser<Person> {

    @Inject
    public IngegneriaCercapersoneParser() {
    }

    @Override
    public List<Person> parse(Document document) {
        List<Person> persons = new ArrayList<Person>();

        Elements elements = document.getElementsByTag("item");

        for (Element element:elements){

                String nome = element.getElementsByTag("title").text();
                String ruolo = element.getElementsByTag("position").text();
                String email = element.getElementsByTag("email").text();
                String ufficio = element.getElementsByTag("address").text();
                String telefono = element.getElementsByTag("telephone").text();
                String webPage = element.getElementsByTag("personalwebpage").text();
                String tutoring = element.getElementsByTag("tutoring").text();

            Person person = new Person(nome, ruolo, email, telefono, ufficio, webPage, tutoring);
            persons.add(person);
        }

        if(persons != null){
            return persons;
        }
        else{
            Log.e("PERSON LIST","Empty persons");
            return null;
        }

    }
}
