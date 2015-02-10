package solutions.alterego.android.unisannio.giurisprudenza;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import solutions.alterego.android.unisannio.IParser;

public class GiurisprudenzaParser implements IParser {

    public List<Article> parse(Document document) {
        Elements elements = document.select("table.lista > tbody > tr[class^=row]");
        List<Article> articles = new ArrayList<>();

        for (Element e : elements) {
            String title = e.select(".title").first().text();

            String pubDate = e.select("span.nota").first().text().split("Pubblicato il:")[1].replace(")", "");
            String link = e.select("a").attr("href");

            articles.add(new Article(title, link, "", pubDate, ""));
        }
        return articles;
    }
}