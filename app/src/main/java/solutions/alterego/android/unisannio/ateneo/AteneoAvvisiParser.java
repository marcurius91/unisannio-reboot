package solutions.alterego.android.unisannio.ateneo;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import solutions.alterego.android.unisannio.IParser;

public class AteneoAvvisiParser implements IParser {

    public List<AteneoNews> parse(Document doc) {
        List<AteneoNews> newsList = new ArrayList<>();

        Elements newsItems = doc.select("div.meta > table > tbody > tr");

        for (int i = 2; i < newsItems.size(); i++) {
            String date = null;
            Element dateElement = newsItems.get(i).select("p").first();
            if (dateElement != null) {
                date = dateElement.text();
            }

            String body = null;
            Element bodyElement = newsItems.get(i).select("a").first();
            String id = "";
            if (bodyElement != null) {
                body = bodyElement.text();

                String href = bodyElement.attr("href");
                id = href.substring(href.indexOf("=") + 1);
            }

            if (date != null && body != null) {
                newsList.add(new AteneoNews(date, body, id));
            }
        }
        return newsList;
    }
}
