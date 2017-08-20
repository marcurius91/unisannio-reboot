package solutions.alterego.android.unisannio.ateneo;

import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import solutions.alterego.android.unisannio.interfaces.IParser;
import solutions.alterego.android.unisannio.models.Article;

public class AteneoAvvisiParser implements IParser {

    public List<Article> parse(Document doc) {

        List<Article> newsList = new ArrayList<>();
        String date = null;
        String title = null;
        String id = null;

        /*
        Elements newsItems = doc.select("div.meta > table > tbody > tr");

        for (int i = 2; i < newsItems.size(); i++) {
            String date = null;
            Element dateElement = newsItems.get(i).select("p").first();
            if (dateElement != null) {
                date = dateElement.text();
            }

            String title = null;
            Element bodyElement = newsItems.get(i).select("a").first();
            String id = "";
            if (bodyElement != null) {
                title = bodyElement.text();

                String href = bodyElement.attr("href");
                id = href.substring(href.indexOf("=") + 1);
            }

            if (date != null && title != null) {
                DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
                DateTime jodatime = dtf.parseDateTime(date);
                newsList.add(new Article(title, id, "", jodatime, ""));
            }
        }
        */

        Elements newsItems = doc.select("div.view-content").select("tbody").select("tr");

        for(int i = 1; i < newsItems.size(); i++) {

            Elements bodyElement = newsItems.get(i).select("td");
            if (bodyElement != null) {

                //get the id of the news.
                Element id_element = bodyElement.get(0);
                id = id_element.text();

                //get the Title of the news.
                Element title_element = bodyElement.get(2).select("a").first();
                title = title_element.text();

                //get the date of the news
                Element date_element = bodyElement.get(3).select("span").first();
                date = date_element.text();

            }

            if(title != null && date != null){
                DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
                DateTime jodatime = dtf.parseDateTime(date);
                newsList.add(new Article(title,id,"",jodatime,""));
            }

        }

        return newsList;

    }
}
