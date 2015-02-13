package solutions.alterego.android.unisannio.ateneo;

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
                newsList.add(Article.builder().title(title).date(date).url(id).build());
            }
        }
        return newsList;
    }
}
