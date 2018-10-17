package solutions.alterego.android.unisannio.ingegneria;

import android.support.annotation.NonNull;
import java.util.UUID;
import org.joda.time.DateTime;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import solutions.alterego.android.unisannio.interfaces.Parser;
import solutions.alterego.android.unisannio.models.Article;
import solutions.alterego.android.unisannio.utils.DateUtils;

public class IngegneriaAvvisiDipartimentoParser implements Parser<Article> {

    @NonNull @Override
    public List<Article> parse(@NonNull Document document) {
        List<Article> list = new ArrayList<>();

        Elements elements = document.select("item");


         for (Element element: elements){
             String title = element.select("title").text();
             String body = clearHtml(element.select("description").text());
             String date = element.select("pubdate").text();
             String author = element.select("author").text();
             String link = element.select("guid").text();

             DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
             DateTime jodatime = dtf.parseDateTime(DateUtils.convertMonth(date.substring(5)));

             list.add(new Article(UUID.randomUUID().toString(), title, "", link, body, jodatime.toString()));
         }

        return list;
    }

    //Method that remove all the htlm tag from the body of the article.
    private String clearHtml(String html){
        String tmp1 = Jsoup.clean(html,Whitelist.none());
        return tmp1.replace("&nbsp;","").replace("&gt;","");
    }
}
