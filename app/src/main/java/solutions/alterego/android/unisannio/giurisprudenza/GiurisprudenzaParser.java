package solutions.alterego.android.unisannio.giurisprudenza;

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

class GiurisprudenzaParser implements IParser<Article> {

    public List<Article> parse(Document document) {
        Elements elements = document.select("table.lista > tbody > tr[class^=row]");
        List<Article> articles = new ArrayList<>();

        for (Element e : elements) {
            String title = e.select(".title").first().text();

            String date = e.select("span.nota").first().text().split("Pubblicato il:")[1].replace(")", "");
            String link = e.select("a").attr("href");

            //01-10-2015 alle 13:28:04
            DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyyHH:mm:SS");
            DateTime jodatime = dtf.parseDateTime(date.trim().replace("alle", "").replace(" ", "").substring(1));

            articles.add(new Article(title, link, "", jodatime, ""));
        }
        return articles;
    }
}
