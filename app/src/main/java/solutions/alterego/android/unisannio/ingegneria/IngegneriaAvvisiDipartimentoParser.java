package solutions.alterego.android.unisannio.ingegneria;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import solutions.alterego.android.unisannio.URLS;
import solutions.alterego.android.unisannio.interfaces.IParser;
import solutions.alterego.android.unisannio.models.Article;

public class IngegneriaAvvisiDipartimentoParser implements IParser {

    @Override
    public List parse(Document document) {
        Elements elements = document.select(".items-leading");

        List<Article> list = new ArrayList<>();

        for (Element element : elements) {
            String title = element.select(".leading-0 > h2").first().text();

            String body = element.select(".leading-0 > p").first().text();

            String date = element.select(".published").first().text().replace("Pubblicato ", "");

            list.add(Article.builder().title(title).url(URLS.INGEGNERIA_NEWS_DIPARTIMENTO).body(body).date(date).build());
        }
        return list;
    }
}
