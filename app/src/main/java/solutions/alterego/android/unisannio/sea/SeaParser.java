package solutions.alterego.android.unisannio.sea;

import java.util.UUID;
import org.joda.time.DateTime;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import solutions.alterego.android.unisannio.interfaces.Parser;
import solutions.alterego.android.unisannio.models.Article;
import solutions.alterego.android.unisannio.utils.ExtensionKt;

public class SeaParser implements Parser<Article> {

    public List<Article> parse(Document document) {
        Elements elements = document.select("p.simplenewsflash_item");
        List<Article> articles = new ArrayList<>();

        for (Element e : elements) {
            String title = e.text();
            String link = e.select("a").attr("href");
            articles.add(new Article(UUID.randomUUID().toString(), title, "", link, "", ExtensionKt.toIso8601(DateTime.now())));
        }
        return articles;
    }
}
