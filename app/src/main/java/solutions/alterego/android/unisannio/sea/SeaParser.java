package solutions.alterego.android.unisannio.sea;

import android.util.Log;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import solutions.alterego.android.unisannio.URLS;
import solutions.alterego.android.unisannio.interfaces.IParser;
import solutions.alterego.android.unisannio.models.Article;

public class SeaParser implements IParser {

    public List<Article> parse(Document document) {

        /*Elements elements = document.select("p.simplenewsflash_item");
        List<Article> articles = new ArrayList<>();

        for (Element e : elements) {
            String title = e.text();
            String link = e.select("a").attr("href");
            articles.add(new Article(title, link, "", null, ""));
        }
        return articles;*/

        List<Article> articles = new ArrayList<>();
        Elements elements = document.select("div.moduletable");
        Elements titles = elements.select("h4.jazin-title");

        for (int i = 0; i< titles.size(); i++){
            Element link = titles.select("a").first();
            String url = URLS.SEA.concat(link.attr("href"));
            String title = titles.get(i).text();
            Log.e("LINK ARTICLE",url);
            articles.add(new Article(title, url, "", null, ""));
        }

        return articles;
    }
}