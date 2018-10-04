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
        Elements elements = document.select("div.nspArt.nspCol1");
        //Elements titles = elements.select("h4.nspHeader.tleft.fnone");

       // Elements bodies=document.body().select("div#ja-current-content");//DA QUELLO INIZIALE DI SEA NEWS

        for (int i = 0; i < elements.size(); i++) {

            Element link = elements.get(i).select("a").first();
            String url = URLS.SEA.concat(link.attr("href"));

            String title = elements.get(i).select("h4.nspHeader.tleft.fnone").text();

            // Element bodies=document.select("p.nspText.tleft.fleft").first();
            String body=elements.get(i).select("p.nspText.tleft.fleft").text();


            articles.add(new Article(UUID.randomUUID().toString(), title, "", url, body, ExtensionKt.toIso8601(DateTime.now())));
        }

        return articles;
    }
}
