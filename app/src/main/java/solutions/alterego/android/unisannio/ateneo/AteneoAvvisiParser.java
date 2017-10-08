package solutions.alterego.android.unisannio.ateneo;

import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import solutions.alterego.android.unisannio.interfaces.IParser;
import solutions.alterego.android.unisannio.models.Article;

public class AteneoAvvisiParser implements IParser<Article> {

    @NonNull public List<Article> parse(@NonNull Document doc) {

        List<Article> newsList = new ArrayList<>();
        String date = null;
        String title = null;
        String id = null;

        Elements newsItems = doc.select("div.view-content").select("tbody").select("tr");

        for (int i = 1; i < newsItems.size(); i++) {
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

            if (title != null && date != null) {
                DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
                DateTime jodatime = dtf.parseDateTime(date);
                newsList.add(new Article(title, id, "", jodatime, ""));
            }
        }

        return newsList;
    }
}
