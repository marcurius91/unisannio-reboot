package solutions.alterego.android.unisannio.giurisprudenza;

import java.util.UUID;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import solutions.alterego.android.unisannio.URLS;
import solutions.alterego.android.unisannio.interfaces.Parser;
import solutions.alterego.android.unisannio.models.Article;
import solutions.alterego.android.unisannio.utils.ExtensionKt;

public class GiurisprudenzaParser implements Parser<Article> {

    public List<Article> parse(Document document) {

        List<Article> articles = new ArrayList<>();
        for (int y = 0; y < 20; y++) {
            Elements elements = document.select("div.leading-"+y+"");


            for (int i = 0; i < elements.size(); i++) {

                Element link = elements.get(i).select("a").first();
                String url = URLS.GIURISPRUDENZA_AVVISI.concat(link.attr("href"));

                String title = elements.get(i).select("div.page-header").text();
                String body=elements.get(i).select("p").text();
                //   String date = e.select("span.nota").first().text().split("Pubblicato il:")[1].replace(")", "");
                // String link = e.select("a").attr("href");

                //01-10-2015 alle 13:28:04
                //DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyyHH:mm:SS");
                //DateTime jodatime = dtf.parseDateTime(date.trim().replace("alle", "").replace(" ", "").substring(1));


                articles.add(new Article(UUID.randomUUID().toString(), title, "", url, body, ExtensionKt.toIso8601(DateTime.now())));
            }
        }
        return articles;
    }
}
