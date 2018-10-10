package solutions.alterego.android.unisannio.scienze;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import solutions.alterego.android.unisannio.URLS;
import solutions.alterego.android.unisannio.interfaces.Parser;
import solutions.alterego.android.unisannio.models.Article;
import solutions.alterego.android.unisannio.utils.DateUtils;
import solutions.alterego.android.unisannio.utils.ExtensionKt;

public class ScienzeParser implements Parser<Article> {

    public List<Article> parse(Document document) {

        Elements elements = document.body().select("div.blog").select("div.item");
        List<Article> newsList = new ArrayList<>();

        String date = null;
        String title = null;
        String body = null;
        String url = null;

        for (Element element : elements) {

            Element titleElement = element.select("h2 >  a[href]").first();
            title = titleElement.text();

            Element bodyElement = element.select("p").first();
            body = bodyElement.text();

            Element dateElement = element.select("dd.published").first();
            date = DateUtils.convertMonthFromScienze(dateElement.text());
            DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MM/yyyy");
            DateTime jodatime = dtf.withLocale(Locale.ITALIAN).parseDateTime(date.replace(".", " ").toLowerCase());

            Elements linkElement = element.select("h2 > a");
            url = URLS.SCIENZE.concat(linkElement.attr("href"));

            String AUTHOR = "Didattica";
            newsList.add(new Article(UUID.randomUUID().toString(), title, AUTHOR, url, body, ExtensionKt.toIso8601(jodatime)));
        }
        /*
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
                DateTimeFormatter dtf = DateTimeFormat.forPattern("dd MMM yyyy");
                DateTime jodatime = dtf.withLocale(Locale.ITALIAN).parseDateTime(date.replace(".", " ").toLowerCase());
                newsList.add(new Article(title, link,"BODY ARTICLE", jodatime, ""));
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
                DateTimeFormatter dtf = DateTimeFormat.forPattern("dd MMM yyyy");
                DateTime jodatime = dtf.withLocale(Locale.ITALIAN).parseDateTime(date.replace(".", " ").toLowerCase());
                newsList.add(new Article(title, link, "", jodatime, ""));
            }
        }
        */
        return newsList;
    }
}
