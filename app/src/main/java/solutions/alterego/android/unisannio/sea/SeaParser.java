package solutions.alterego.android.unisannio.sea;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.joda.time.DateTime;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import solutions.alterego.android.unisannio.URLS;
import solutions.alterego.android.unisannio.interfaces.IParser;
import solutions.alterego.android.unisannio.models.Article;
import solutions.alterego.android.unisannio.utils.ExtensionKt;
import timber.log.Timber;

public class SeaParser implements IParser {

    public List<Article> parse(Document document) {
        List<Article> articles = new ArrayList<>();
        Elements elements = document.select("div.moduletable");
        Elements titles = elements.select("h4.jazin-title");

        for (int i = 0; i < titles.size(); i++) {
            Element link = titles.select("a").first();
            String url = URLS.SEA.concat(link.attr("href"));
            String title = titles.get(i).text();
            Timber.d("LINK ARTICLE", url);
            articles.add(new Article(UUID.randomUUID().toString(), title, "", link.attr("href"), "", ExtensionKt.toIso8601(DateTime.now())));
        }

        return articles;
    }
}
