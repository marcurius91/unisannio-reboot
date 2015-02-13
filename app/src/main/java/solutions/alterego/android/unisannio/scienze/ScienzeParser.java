package solutions.alterego.android.unisannio.scienze;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import solutions.alterego.android.unisannio.models.Article;
import solutions.alterego.android.unisannio.interfaces.IParser;

public class ScienzeParser implements IParser {

    public List<Article> parse(Document document) {
        Elements newsItems = document.select("li.latestnewsfl_green");

        List<Article> newsList = new ArrayList<>();
        for (Element newsItem : newsItems) {
            String date = null;
            Element dateElement = newsItem.select("span").first();
            if (dateElement != null) {
                date = dateElement.text().trim();
            }

            String title = null;
            Element bodyElement = newsItem.select("a").first();
            String link = null;
            if (bodyElement != null) {
                title = bodyElement.text();
                link = bodyElement.attr("href");
            }

            if (date != null && title != null) {
                newsList.add(Article.builder().title(title).url(link).date(date).build());
            }
        }

        newsItems = document.select("li.latestnewsfl_orange");
        for (Element newsItem : newsItems) {
            String date = null;
            Element dateElement = newsItem.select("span").first();
            if (dateElement != null) {
                date = dateElement.text().trim();
            }

            String title = null;
            Element bodyElement = newsItem.select("a").first();
            String link = null;
            if (bodyElement != null) {
                title = bodyElement.text();
                link = bodyElement.attr("href");
            }

            if (date != null && title != null) {
                newsList.add(Article.builder().title(title).url(link).date(date).build());
            }
        }
        return newsList;
    }
}
