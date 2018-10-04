package solutions.alterego.android.unisannio.sea;

import android.net.Uri;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import solutions.alterego.android.unisannio.URLS;
import solutions.alterego.android.unisannio.interfaces.Parser;
import solutions.alterego.android.unisannio.models.Article;
import solutions.alterego.android.unisannio.utils.ExtensionKt;
import timber.log.Timber;

public class SeaParser implements Parser<Article> {

    @NonNull public List<Article> parse(@NonNull Document document) {
        List<Article> articles = new ArrayList<>();
        Elements elements = document.select("div.moduletable");
        Elements titles = elements.select("h4.jazin-title");

       // Elements bodies=document.body().select("div#ja-current-content");//DA QUELLO INIZIALE DI SEA NEWS

        for (int i = 0; i < titles.size(); i++) {
            Element link = titles.select("a").get(i);
            String url = URLS.SEA.concat(link.attr("href"));
            String title = titles.get(i).text();





           Element bodies=document.select("div#ja-current-content").first();
            String body=bodies.text();


            articles.add(new Article(UUID.randomUUID().toString(), title, "", url, document.toString(), ExtensionKt.toIso8601(DateTime.now())));
        }

        return articles;
    }
}
